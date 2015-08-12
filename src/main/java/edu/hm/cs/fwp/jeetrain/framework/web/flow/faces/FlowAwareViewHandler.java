/* FlowAwareViewHandler.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.faces;

import java.util.List;
import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContextFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * A JSF 2.0 view handler which adds awareness of {@link Flow}s to the standard
 * view handling.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public final class FlowAwareViewHandler extends ViewHandlerWrapper {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ViewHandler delegate;

	private FlowContextFactory flowContextFactory;

	public FlowAwareViewHandler(ViewHandler delegate) {
		this.logger.info("*** FLOW *** Decorating existing view handler ["
				+ delegate + "]");
		this.delegate = delegate;
	}

	/**
	 * @see javax.faces.application.ViewHandlerWrapper#getWrapped()
	 */
	@Override
	public ViewHandler getWrapped() {
		return this.delegate;
	}

	/**
	 * Returns a JSF action URL derived from the <code>viewId</code> argument
	 * that is suitable to be used by the {@link NavigationHandler} to issue a
	 * redirect request to the URL using a NonFaces request.
	 * 
	 * @see javax.faces.application.ViewHandlerWrapper#getRedirectURL(javax.faces.context.FacesContext,
	 *      java.lang.String, java.util.Map, boolean)
	 */
	@Override
	public String getRedirectURL(FacesContext context, String viewId,
			Map<String, List<String>> parameters, boolean includeViewParams) {
		String result = null;
		result = getWrapped().getRedirectURL(context, viewId, parameters,
				includeViewParams);
		if (logger.isTraceEnabled()) {
			StringBuilder msg = new StringBuilder();
			msg.append("*** FLOW *** returning redirect URL [");
			msg.append(result);
			msg.append("] for viewId=[");
			msg.append(viewId);
			msg.append("], parameters=[");
			if (parameters != null) {
				for (Map.Entry<String, List<String>> currentEntry : parameters
						.entrySet()) {
					for (String currentValue : currentEntry.getValue()) {
						msg.append("[");
						msg.append(currentEntry.getKey());
						msg.append("]=[");
						msg.append(currentValue);
						msg.append("] ");
					}
				}
			}
			msg.append("] and includeViewParams=[");
			msg.append(includeViewParams);
			msg.append("]");
			logger.trace(msg.toString());
		}
		return result;
	}

	/**
	 * @see javax.faces.application.ViewHandlerWrapper#getActionURL(javax.faces.context.FacesContext,
	 *      java.lang.String)
	 */
	@Override
	public String getActionURL(FacesContext context, String viewId) {
		String result = getWrapped().getActionURL(context, viewId);
		FlowContext flowContext = getFlowContext(context);
		Flow activeFlow = flowContext.getActiveFlow();
		if (activeFlow != null
				&& isFacesViewMatchingFlowView(viewId,
						activeFlow.getCurrentViewPath())) {
			result = getActionURLFromActiveFlow(context, viewId, flowContext,
					activeFlow);
		} else {
			result = getWrapped().getActionURL(context, viewId);
		}
		if (logger.isTraceEnabled()) {
			StringBuilder msg = new StringBuilder();
			msg.append("*** FLOW *** returning action URL [");
			msg.append(result);
			msg.append("] for viewId=[");
			msg.append(viewId);
			msg.append("]");
			logger.trace(msg.toString());
		}
		return result;
	}

	/**
	 * Derives the view ID from the current flow and the specified action
	 * outcome, if there is an active flow; otherwise simply delegates to
	 * orginal implementation.
	 * 
	 * @see javax.faces.application.ViewHandlerWrapper#deriveViewId(javax.faces.context.FacesContext,
	 *      java.lang.String)
	 */
	@Override
	public String deriveViewId(FacesContext context, String rawViewId) {
		String result = null;
		result = getWrapped().deriveViewId(context, rawViewId);
		logger.trace(
				"*** FLOW *** returning derived view ID [{}] for rawViewId=[{}]",
				result, rawViewId);
		return result;
	}

	/**
	 * Retrieves the flow context factory from the Spring web application
	 * context.
	 */
	private FlowContextFactory getFlowContextFactory(FacesContext facesContext) {
		if (null == this.flowContextFactory) {
			this.flowContextFactory = CDIUtils
					.lookupBean(FlowContextFactory.class);
		}
		return this.flowContextFactory;
	}

	/**
	 * Returns the current flow context.
	 */
	private FlowContext getFlowContext(FacesContext facesContext) {
		return getFlowContextFactory(facesContext).getCurrentInstance(
				(HttpServletRequest) facesContext.getExternalContext()
						.getRequest());
	}

	/**
	 * Builds an action URL which will identify the currently active flow when
	 * used in an upcoming POST request.
	 */
	private String getActionURLFromActiveFlow(FacesContext facesContext,
			String facesViewId, FlowContext flowContext, Flow activeFlow) {
		ViewReference result = null;
		ViewReference flowViewReference = new ViewReference.Builder().withPath(
				activeFlow.getCurrentViewPath()).build();
		if (flowViewReference.isExternal()) {
			// we have a context path => no need to add it
			result = new ViewReference.Builder()
					.withPath(flowViewReference.getPath())
					.withParameter(Flow.FLOW_ID_PARAM_NAME, activeFlow.getId())
					.build();
		} else {
			// we don't have a context path => we need to add it
			result = new ViewReference.Builder()
					.withContextPath(
							flowContext.getCurrentViewReference()
									.getContextPath())
					.withInternalPath(flowViewReference.getPath())
					.withParameter(Flow.FLOW_ID_PARAM_NAME, activeFlow.getId())
					.build();
		}
		return result.toString();
	}

	/**
	 * Returns <code>true</code>, if the given JSF view id matches the give flow
	 * view path; otherwise <code>false</code>.
	 * <p>
	 * Any suffices like {@code .xhtml} or {@code .jsf} are ignored when
	 * comparing both parameters.
	 * </p>
	 */
	private boolean isFacesViewMatchingFlowView(String facesViewId,
			String flowViewPath) {
		boolean result = false;
		int facesViewIdSuffixStart = facesViewId.lastIndexOf('.');
		result = facesViewId.regionMatches(0, flowViewPath, 0,
				facesViewIdSuffixStart != -1 ? facesViewIdSuffixStart
						: facesViewId.length());
		return result;
	}
}
