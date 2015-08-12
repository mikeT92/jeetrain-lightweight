/* FlowContextImpl.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycle;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Implementation of a {@link FlowInstance}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 26.01.2012 11:43:43
 */
final class FlowContextImpl implements FlowContext {

	public static final String ATTRIBUTE_NAME = "x20.web.flow.context";

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final FlowStack flowStack;
	private final FlowFactory flowFactory;
	private final FlowLifecycle flowLifecycle;
	private final Map<String, Object> flowScopeBeanStore;
	private final ViewReference currentViewReference;
	private ViewReference nextViewReference;
	private String consumedEvent;

	public FlowContextImpl(HttpServletRequest request, HttpServletResponse response,
			FlowStack flowStack, FlowFactory flowFactory, FlowLifecycle flowLifecyle,
			Map<String, Object> flowScopeBeanStore) {
		this.request = request;
		this.response = response;
		this.flowStack = flowStack;
		this.flowFactory = flowFactory;
		this.flowLifecycle = flowLifecyle;
		this.flowScopeBeanStore = flowScopeBeanStore;
		this.currentViewReference =
				new ViewReference.Builder()
						.withPath(request.getRequestURI()).withQuery(request.getQueryString())
						.build();
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getRequest()
	 */
	@Override
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getResponse()
	 */
	@Override
	public HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getCurrentViewReference()
	 */
	@Override
	public ViewReference getCurrentViewReference() {
		return this.currentViewReference;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getActiveFlow()
	 */
	@Override
	public Flow getActiveFlow() {
		return this.flowStack.getActiveFlow();
	}

	/**
	 * Pushes the specified flow onto the flow stack and fires an
	 * AfterFlowScopeCreatedEvent afterwards if the given flow is the first flow
	 * to be put onto the stack.
	 * 
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#pushFlow(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow)
	 */
	@Override
	public void pushFlow(Flow flow) {
		this.flowStack.pushFlow(flow);
		if (this.flowStack.size() == 1) {
			this.flowLifecycle.fireAfterFlowScopeCreatedEvent(this, this.flowScopeBeanStore);
		}
	}

	/**
	 * Fires a BeforeScopeDestroyedEvent if there is only one flow left on the
	 * flow stack before it is actually removed from it to notify all lifecycle
	 * listeners that flow scoped beans are about to be destroyed. After the
	 * removal of the last flow, the flow-scoped beanstore is cleared to destroy
	 * all flow scoped beans.
	 * 
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#popFlow()
	 */
	@Override
	public Flow popFlow() {
		Flow result = null;
		if (this.flowStack.size() == 1) {
			this.flowLifecycle.fireBeforeScopeDestroyedEvent(this, this.flowScopeBeanStore);
		}
		result = this.flowStack.popFlow();
		if (result != null && this.flowStack.size() == 0) {
			this.flowScopeBeanStore.clear();
		}
		return result;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getNumberOfRunningFlows()
	 */
	@Override
	public int getNumberOfRunningFlows() {
		return this.flowStack.size();
	}

	@Override
	public ViewReference getNextViewReference() {
		return this.nextViewReference;
	}

	@Override
	public void sendRedirect(ViewReference redirectViewReference) {
		String encodedRedirectUrl =
				this.response.encodeRedirectURL(redirectViewReference.toString());
		try {
			this.response.sendRedirect(encodedRedirectUrl);
		} catch (IOException ex) {
			throw new IllegalStateException("Unable to send redirect to location ["
					+ encodedRedirectUrl + "]: " + ex.getMessage(), ex);
		}
		this.nextViewReference = redirectViewReference;
	}

	@Override
	public FlowFactory getFlowFactory() {
		return this.flowFactory;
	}

	@Override
	public void redirectRequired(ViewReference redirectViewReference) {
		this.nextViewReference = redirectViewReference;
	}

	@Override
	public boolean isRedirectRequired() {
		return this.nextViewReference != null;
	}

	@Override
	public boolean isResponseComplete() {
		return this.response.isCommitted();
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#consumeEvent(java.lang.String)
	 */
	@Override
	public void consumeEvent(String eventName) {
		this.consumedEvent = eventName;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getConsumedEvent()
	 */
	@Override
	public String getConsumedEvent() {
		return this.consumedEvent;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#isEventConsumed()
	 */
	@Override
	public boolean isEventConsumed() {
		return this.consumedEvent != null;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getFlowLifecycle()
	 */
	@Override
	public FlowLifecycle getFlowLifecycle() {
		return this.flowLifecycle;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext#getFlowScopeBeanStore()
	 */
	@Override
	public Map<String, Object> getFlowScopeBeanStore() {
		return this.flowScopeBeanStore;
	}
}
