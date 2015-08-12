/* FlowLifecycleListener.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import java.util.Map;

/**
 * {@code FlowLifecycleListener} enables clients of the flow engine to listen to
 * lifecycle events fired by the flow engine.
 * <p>
 * FlowLifecycleListeners can be specified when HTTP requests are passed to the
 * flow engine with
 * {@link FlowRequestInterceptor#beforeProcessingRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
 * . The registration of a listener last for the lifetime of the current HTTP
 * request.
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.01.2012 15:38:49
 */
public interface FlowLifecycleListener {

	/**
	 * Will be called by the flow engine whenever a new flow scope has been
	 * created.
	 * <p>
	 * Enables listeners to perform some custom post-construct actions on all
	 * flow-scoped beans.
	 * </p>
	 * 
	 * @param flowContext
	 *            flow context
	 * @param flowScopeBeanStore
	 *            map used by the flow engine to store flow scoped beans.
	 */
	public void afterFlowScopeCreated(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore);

	/**
	 * Will be called by the flow engine whenever a flow scope is about to be
	 * destroyed.
	 * <p>
	 * Enables listeners to perform some custom pre-destroy actions on all
	 * flow-scoped beans.
	 * </p>
	 * 
	 * @param flowContext
	 *            flow context
	 * @param flowScopeBeanStore
	 *            map used by the flow engine to store flow scoped beans.
	 */
	public void beforeFlowScopeDestroyed(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore);
}
