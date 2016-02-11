/* FlowAwareNavigationHandler.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.faces;

import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContextFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowNavigationHandler;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Custom implementation of a {@link ConfigurableNavigationHandler} which
 * decorates an existing {@code NavigationHandler} adding flow-support.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public final class FlowAwareNavigationHandler extends ConfigurableNavigationHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ConfigurableNavigationHandler delegate;

	private FlowContextFactory flowContextFactory;

	private FlowNavigationHandler flowNavigationHandler;

	/**
	 * Konstruktor mit dem Original-NavigationHandler, an den alle
	 * Methodenaufrufe weitergeleitet werden sollen.
	 */
	public FlowAwareNavigationHandler(NavigationHandler delegate) {
		this.logger.info("Decorating existing navigation handler [" + delegate + "]");
		this.delegate = (ConfigurableNavigationHandler) delegate;
	}

	/**
	 * Performs navigation based on the specified action and outcome respecting
	 * active flows.
	 * <p>
	 * If no active flow is present, all processing is delegated to the original
	 * navigation handler.
	 * </p>
	 * 
	 * @see javax.faces.application.NavigationHandler#handleNavigation(javax.faces.context.FacesContext,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void handleNavigation(FacesContext facesContext, String fromAction, String outcome) {
		this.logger.trace(
				"*** FLOW *** handling navigation from action [{}] with outcome [{}]", fromAction,
				outcome);
		if (!handleFlowEvent(facesContext, fromAction, outcome)) {
			this.delegate.handleNavigation(facesContext, fromAction, outcome);
		}
	}

	/**
	 * @see javax.faces.application.ConfigurableNavigationHandler#getNavigationCase(javax.faces.context.FacesContext,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome) {
		NavigationCase result = this.delegate.getNavigationCase(context, fromAction, outcome);
		if (this.logger.isTraceEnabled()) {
			this.logger
					.trace(
							"*** FLOW *** returning navigation case [{}] from action [{}] with outcome [{}]",
							new Object[] { result, fromAction, outcome });
		}
		return result;
	}

	/**
	 * @see javax.faces.application.ConfigurableNavigationHandler#getNavigationCases()
	 */
	@Override
	public Map<String, Set<NavigationCase>> getNavigationCases() {
		return this.delegate.getNavigationCases();
	}

	/**
	 * Delegates the specified navigation to the flow engine if a current flow
	 * exists.
	 * 
	 * @return <code>true</code>, if the given navigation has been processed by
	 *         the currently active flow; <code>false</code> otherwise.
	 */
	private boolean handleFlowEvent(FacesContext facesContext, String fromAction, String event) {

		if (StringUtils.isEmpty(event)) {
			return false;
		}

		FlowContext flowContext = getFlowContext(facesContext);
		FlowNavigationHandler handler = getFlowNavigationHandler(facesContext);
		handler.handleNavigation(flowContext, event);
		if (flowContext.isEventConsumed()) {
			// event has been consumed
			if (flowContext.isResponseComplete()) {
				// propagate response complete to JSF
				facesContext.responseComplete();
			} else if (flowContext.isRedirectRequired()) {
				// perform requested redirect
				String redirectUrl = null;
				redirectUrl =
						facesContext.getExternalContext().encodeActionURL(
								flowContext.getNextViewReference().toString());
				try {
					clearViewMapIfNecessary(
							facesContext.getViewRoot(), flowContext.getNextViewReference());
					updateRenderTargets(facesContext, flowContext.getNextViewReference());
					facesContext.getExternalContext().getFlash().setRedirect(true);
					facesContext.getExternalContext().redirect(redirectUrl);
				} catch (java.io.IOException ioe) {
					this.logger.error("*** FLOW *** redirect to URL [" + redirectUrl + "] failed: "
							+ ioe.getMessage(), ioe);
					throw new FacesException(ioe.getMessage(), ioe);
				}
				facesContext.responseComplete();
			} else if (!flowContext.getCurrentViewReference().equals(
					flowContext.getNextViewReference())) {
				// perform requested forward
				ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
				UIViewRoot newRoot =
						viewHandler.createView(facesContext, flowContext
								.getNextViewReference().getPath());
				updateRenderTargets(facesContext, flowContext.getNextViewReference());
				facesContext.setViewRoot(newRoot);
			}
		}
		return flowContext.isEventConsumed();
	}

	/**
	 * Retrieves the flow navigation handler from the Spring web application
	 * context.
	 */
	private FlowNavigationHandler getFlowNavigationHandler(FacesContext facesContext) {
		if (null == this.flowNavigationHandler) {
			this.flowNavigationHandler = CDIUtils.lookupBean(FlowNavigationHandler.class);
		}
		return this.flowNavigationHandler;
	}

	/**
	 * Retrieves the flow context factory from the Spring web application
	 * context.
	 */
	private FlowContextFactory getFlowContextFactory(FacesContext facesContext) {
		if (null == this.flowContextFactory) {
			this.flowContextFactory = CDIUtils.lookupBean(FlowContextFactory.class);
		}
		return this.flowContextFactory;
	}

	/**
	 * Returns the current flow context.
	 */
	private FlowContext getFlowContext(FacesContext facesContext) {
		return getFlowContextFactory(facesContext).getCurrentInstance(
				(HttpServletRequest) facesContext.getExternalContext().getRequest());
	}

	/**
	 * Calls <code>clear()</code> on the ViewMap (if available) if the view ID
	 * of the UIViewRoot differs from <code>newId</code>
	 */
	private void clearViewMapIfNecessary(UIViewRoot root, ViewReference newViewId) {
		if (!root.getViewId().equals(newViewId.getPath())) {
			Map<String, Object> viewMap = root.getViewMap(false);
			if (viewMap != null) {
				viewMap.clear();
			}
		}
	}

	/**
	 * Make sure that partial request processing and especially partial view
	 * rendering works as expected after the flow engine requested or committed
	 * a redirect.
	 */
	private void updateRenderTargets(FacesContext ctx, ViewReference newViewId) {
		if (!ctx.getViewRoot().getViewId().equals(newViewId.getPath())) {
			PartialViewContext pctx = ctx.getPartialViewContext();
			if (!pctx.isRenderAll()) {
				pctx.setRenderAll(true);
			}
		}
	}
}
