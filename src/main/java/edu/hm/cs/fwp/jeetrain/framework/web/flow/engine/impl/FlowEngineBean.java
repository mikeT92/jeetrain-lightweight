/* FlowEngineBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.Enumeration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContextFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowEventProcessor;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycleListener;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowNavigationHandler;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowRequestInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Implementation of a FlowEngine based on a Spring managed POJO.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 17:33:45
 */
@Named
public final class FlowEngineBean implements FlowRequestInterceptor, FlowNavigationHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private FlowFactory flowFactory;

	@Inject
	private FlowContextFactory flowContextFactory;

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowRequestInterceptor#beforeProcessingRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean beforeProcessingRequest(HttpServletRequest request,
			HttpServletResponse response, FlowLifecycleListener listener) {
		FlowContext flowContext = this.flowContextFactory.createInstance(request, response);
		if (listener != null) {
			flowContext.getFlowLifecycle().registerListener(listener);
		}
		return beforeProcessingRequest(flowContext);
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowRequestInterceptor#afterProcessingRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void afterProcessingRequest(HttpServletRequest request, HttpServletResponse response) {
		afterProcessingRequest(this.flowContextFactory.getCurrentInstance(request));
	}

	/**
	 * Return the current {@link FlowContext} bound to the specified HTTP
	 * request.
	 */
	public FlowContext getFlowContext(HttpServletRequest request) {
		return this.flowContextFactory.getCurrentInstance(request);
	}

	/**
	 * Post-processes the current HTTP request by performing some clean-up
	 * activities.
	 */
	private void afterProcessingRequest(FlowContext flowContext) {
		this.flowContextFactory.disposeInstance(flowContext);
	}

	private boolean beforeProcessingRequest(FlowContext flowContext) {
		handleFlowRequest(flowContext);
		return !flowContext.isResponseComplete();
	}

	/**
	 * Passes the given navigation event to the flow engine.
	 */
	public void handleNavigation(FlowContext flowContext, String eventName) {
		Flow activeFlow = flowContext.getActiveFlow();
		if (activeFlow != null) {
			FlowEventProcessor processor = (FlowEventProcessor) activeFlow;
			processor.processEvent(flowContext, eventName);
		}
	}

	/**
	 * Handles the current HTTP request depending on the current state of the
	 * flow engine.
	 * <p>
	 * In order to avoid concurrency issues with incoming requests, all
	 * flow-engine related request pre-processing is synchronized using the
	 * current HTTP session as a monitor.
	 * </p>
	 */
	private void handleFlowRequest(FlowContext flowContext) {

		synchronized (flowContext.getRequest().getSession()) {
			Flow activeFlow = flowContext.getActiveFlow();
			String flowIdParameter = flowContext.getRequest().getParameter(Flow.FLOW_ID_PARAM_NAME);

			if (activeFlow != null) {
				// there is an active flow
				if (flowIdParameter != null && activeFlow.getId().equals(flowIdParameter)) {
					handleInsideFlowRequest(flowContext, activeFlow);
				} else {
					handleOutOfFlowRequest(flowContext, activeFlow);
				}
			} else {
				// there is no active flow
				if (flowIdParameter != null) {
					handleFakeFlowRequest(flowContext);
				} else {
					if ("GET".equals(flowContext.getRequest().getMethod())) {
						Flow newFlow =
								this.flowFactory.createFlowFromViewReference(flowContext
										.getCurrentViewReference());
						if (newFlow != null) {
							handleNewFlowRequest(flowContext, newFlow);
						}
					}
				}
			}
		}
	}

	/**
	 * Handles HTTP requests which can be related to the currently active flow.
	 * <p>
	 * Actually this method does nothing, since the HTTP request will be
	 * delegated to the flow engine at a later time (i.e. while processing
	 * navigation events in {@link #handleNavigation(FlowContext, String)}).
	 * </p>
	 */
	private void handleInsideFlowRequest(FlowContext flowContext, Flow activeFlow) {
		if (this.logger.isDebugEnabled()) {
			StringBuilder logEntry = new StringBuilder();
			logEntry.append("*** FLOW *** handleInsideFlowRequest: flow [");
			logEntry.append(activeFlow);
			logEntry.append("] state [");
			logEntry.append(activeFlow.getCurrentState().getName());
			logEntry.append("]: request [");
			logEntry.append(flowContext.getCurrentViewReference());
			logEntry.append("] method [");
			logEntry.append(flowContext.getRequest().getMethod());
			logEntry.append("] is forwarded to active flow...");
			this.logger.debug(logEntry.toString());
		}
	}

	/**
	 * Handles HTTP requests which cannot be related to the currently active
	 * flow.
	 * <p>
	 * These requests are handled the following way:
	 * </p>
	 * <ol>
	 * <li>If the current request is a GET request, all running flows are
	 * aborted.
	 * <li>
	 * <li>If the current request is a POST request, the request is redirected
	 * to the current flow view assuming a duplicate form submission.
	 * <li>
	 * <ol>
	 */
	private void handleOutOfFlowRequest(FlowContext flowContext, Flow activeFlow) {
		if ("GET".equals(flowContext.getRequest().getMethod())) {
			// active flow does not accept the request and the current
			// request is a GET request
			// => abort all running flows and continue with request
			// processing
			if (this.logger.isDebugEnabled()) {
				StringBuilder logEntry = new StringBuilder();
				logEntry.append("*** FLOW *** handleOutOfFlowRequest: flow [");
				logEntry.append(activeFlow);
				logEntry.append("] state [");
				logEntry.append(activeFlow.getCurrentState().getName());
				logEntry.append("]: request [");
				logEntry.append(flowContext.getCurrentViewReference());
				logEntry.append("] method [");
				logEntry.append(flowContext.getRequest().getMethod());
				logEntry.append("] aborts all running flows.");
				this.logger.debug(logEntry.toString());
			}
			abortAllRunningFlows(flowContext);
		} else {
			// active flow does not accept the request and the current
			// request is a POST request which can only happen with
			// duplicate form submits
			// => redisplay current flow view and stop any further request
			// processing
			ViewReference.Builder builder =
					new ViewReference.Builder()
							.withContextPath(flowContext.getCurrentViewReference().getContextPath())
							.withInternalPath(activeFlow.getCurrentViewPath())
							.withParameter(Flow.FLOW_ID_PARAM_NAME, activeFlow.getId());
			flowContext.sendRedirect(builder.build());
			if (this.logger.isDebugEnabled()) {
				StringBuilder logEntry = new StringBuilder();
				logEntry.append("*** FLOW *** handleOutOfFlowRequest: flow [");
				logEntry.append(activeFlow);
				logEntry.append("] state [");
				logEntry.append(activeFlow.getCurrentState().getName());
				logEntry.append("]: request [");
				logEntry.append(flowContext.getCurrentViewReference());
				logEntry.append("] method [");
				logEntry.append(flowContext.getRequest().getMethod());
				logEntry.append("] forces redirect to current flow view [");
				logEntry.append(flowContext.getNextViewReference());
				logEntry.append("] assuming duplicate form submission.");
				this.logger.debug(logEntry.toString());
			}
		}
	}

	/**
	 * Handles HTTP requests which seem to be related to an active flow (i.e.
	 * the flow-ID parameter is present) but currently there is no active flow.
	 * <p>
	 * These requests are handled the following way:
	 * </p>
	 * <ol>
	 * <li>If the current request is a GET request, the method does nothing to
	 * enable the reactivation of the referenced flow at a later time. This
	 * should handle all cases when users bookmarked an URL containing the flow
	 * ID parameter.
	 * <li>
	 * <li>If the current request is a POST request, the request is redirected
	 * to the application homepage assuming a duplication form submission which
	 * triggered an exit transition from the last active flow. This should
	 * handle all cases when users doubleclick on buttons which trigger a flow
	 * exit transition.
	 * <li>
	 * <ol>
	 */
	private void handleFakeFlowRequest(FlowContext flowContext) {
		if ("POST".equals(flowContext.getRequest().getMethod())) {
			ViewReference redirectViewReference =
					new ViewReference.Builder().withContextPath(
							flowContext.getCurrentViewReference().getContextPath()).build();
			flowContext.sendRedirect(redirectViewReference);
			if (this.logger.isDebugEnabled()) {
				StringBuilder logEntry = new StringBuilder();
				logEntry.append("*** FLOW *** handleFakeFlowRequest: request [");
				logEntry.append(flowContext.getCurrentViewReference());
				logEntry.append("] method [");
				logEntry.append(flowContext.getRequest().getMethod());
				logEntry.append("] using unknown flow ID [");
				logEntry.append(flowContext.getRequest().getParameter(Flow.FLOW_ID_PARAM_NAME));
				logEntry.append("] forces redirect to application homepage [");
				logEntry.append(flowContext.getNextViewReference());
				logEntry
						.append("] assuming duplicate form submission to a previously closed flow.");
				this.logger.debug(logEntry.toString());
			}
		} else {
			if (this.logger.isDebugEnabled()) {
				StringBuilder logEntry = new StringBuilder();
				logEntry.append("*** FLOW *** handleFakeFlowRequest: request [");
				logEntry.append(flowContext.getCurrentViewReference());
				logEntry.append("] method [");
				logEntry.append(flowContext.getRequest().getMethod());
				logEntry.append("] using unknown flow ID [");
				logEntry.append(flowContext.getRequest().getParameter(Flow.FLOW_ID_PARAM_NAME));
				logEntry
						.append("] will start a new flow if any flow matches the specified request URL.");
				this.logger.debug(logEntry.toString());
			}
		}
	}

	/**
	 * Handles an HTTP request that is supposed to activate a new flow.
	 * <p>
	 * New flows are handled the following way:
	 * </p>
	 * <ol>
	 * <li>The given new flow is activated.
	 * <li>
	 * <li>The given new flow is pushed onto the flow stack making it the new
	 * active flow.
	 * <li>
	 * <li>A redirect is sent to display the default start view of the new
	 * active flow with a subsequent GET request.
	 * <li>
	 * <ol>
	 */
	@SuppressWarnings("rawtypes")
	private void handleNewFlowRequest(FlowContext flowContext, Flow newFlow) {
		newFlow.activate(flowContext, null, null);
		flowContext.pushFlow(newFlow);
		ViewReference.Builder builder =
				new ViewReference.Builder().withPath(flowContext
						.getCurrentViewReference().getPath());
		Enumeration parameterNames = flowContext.getRequest().getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String currentName = (String) parameterNames.nextElement();
			if (!Flow.FLOW_ID_PARAM_NAME.equals(currentName)) {
				builder.withParameter(
						currentName, flowContext.getRequest().getParameter(currentName));
			}
		}
		builder.withParameter(Flow.FLOW_ID_PARAM_NAME, newFlow.getId());
		flowContext.sendRedirect(builder.build());
		if (this.logger.isDebugEnabled()) {
			StringBuilder logEntry = new StringBuilder();
			logEntry.append("*** FLOW *** handleNewFlowRequest: flow [");
			logEntry.append(newFlow);
			logEntry.append("] state [");
			logEntry.append(newFlow.getCurrentState().getName());
			logEntry.append("]: request [");
			logEntry.append(flowContext.getCurrentViewReference());
			logEntry.append("] method [");
			logEntry.append(flowContext.getRequest().getMethod());
			logEntry.append("] activated new flow causing redirect to [");
			logEntry.append(flowContext.getNextViewReference());
			logEntry.append("]");
			this.logger.debug(logEntry.toString());
		}
	}

	/**
	 * Aborts all running flows and fires the BeforeScopeDestroyedEvent.
	 */
	private void abortAllRunningFlows(FlowContext flowContext) {
		Flow current = flowContext.popFlow();
		while (current != null) {
			current.deactivate(flowContext);
			current = flowContext.popFlow();
		}
	}
}
