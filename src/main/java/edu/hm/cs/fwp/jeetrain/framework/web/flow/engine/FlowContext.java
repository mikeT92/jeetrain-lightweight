/* FlowContext.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Flow context representing the current state of the lifecycle processing of
 * all active flows.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public interface FlowContext {

	/**
	 * Returns the current HTTP request.
	 */
	public HttpServletRequest getRequest();

	/**
	 * Returns the current HTTP response.
	 */
	public HttpServletResponse getResponse();

	/**
	 * Sends a redirect to the specified redirect view reference.
	 * 
	 * @throws IllegalStateException
	 *             if the redirect fails.
	 */
	public void sendRedirect(ViewReference redirectViewReference);

	/**
	 * Requests a redirect to the specified view references to be executed when
	 * the processing of the current request is completed.
	 */
	public void redirectRequired(ViewReference redirectViewReference);

	/**
	 * Returns <code>true</code>, if a redirect has been previously requested by
	 * calling {@code redirectRequired(ViewReference)}; otherwise
	 * <code>false</code>.
	 */
	public boolean isRedirectRequired();

	/**
	 * Returns <code>true</code>, if a response has been committed and sent to
	 * the client; otherwise <code>false</code>.
	 */
	public boolean isResponseComplete();

	/**
	 * Tells the flow engine to consider the specified event as consumed
	 * skipping all further event processing.
	 */
	public void consumeEvent(String eventName);

	/**
	 * Returns the name of the last consumed event.
	 */
	public String getConsumedEvent();

	/**
	 * Returns <code>true</code>, if the current event has been consumed and
	 * further event processing must be skipped; otherwise <code>false</code>.
	 */
	public boolean isEventConsumed();

	/**
	 * Returns the view reference that represents the request URL of the current
	 * HTTP request.
	 */
	public ViewReference getCurrentViewReference();

	/**
	 * Returns the view reference of next view that has to be rendered.
	 */
	public ViewReference getNextViewReference();

	/**
	 * Returns the currently active flow (i.e. top of the flow stack) if the
	 * flow stack is not empty; otherwise <code>null</code>.
	 */
	public Flow getActiveFlow();

	/**
	 * Pushes the specified flow onto the flow stack which makes the given flow
	 * the currently active flow.
	 */
	public void pushFlow(Flow flow);

	/**
	 * Returns the top flow of the flow stack after removing it, if the stack is
	 * not empty; otherwise <code>null</code>.
	 */
	public Flow popFlow();

	/**
	 * Returns the number of running flows on the flow stack.
	 */
	public int getNumberOfRunningFlows();

	/**
	 * Returns the {@link FlowFactory} used to create new executable flows.
	 */
	public FlowFactory getFlowFactory();

	/**
	 * Returns the {@link FlowLifecycle} that propagates flow related events to
	 * registered listeners.
	 */
	public FlowLifecycle getFlowLifecycle();

	/**
	 * Returns the {@code FlowScopeBeanStore} that manages flow scoped beans.
	 */
	public Map<String, Object> getFlowScopeBeanStore();
}
