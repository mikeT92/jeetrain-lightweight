/* FlowLifecycle.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import java.util.Map;

/**
 * {@code FlowLifecycle} manages flow engine related lifecycle events.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.01.2012 16:35:14
 */
public interface FlowLifecycle {

	/**
	 * Registers the specified {@link FlowLifecycleListener} to receive
	 * notifications about flow lifecyle events.
	 */
	public void registerListener(FlowLifecycleListener listener);

	/**
	 * Unregisters the specified {@link FlowLifecycleListener} which does not
	 * want to be notified about flow lifecyle events anymore.
	 */
	public void unregisterListener(FlowLifecycleListener listener);

	/**
	 * Fires the {@code AfterFlowScopeCreated} event and propagates it to all
	 * registered flow lifecycle listeners.
	 */
	public void fireAfterFlowScopeCreatedEvent(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore);

	/**
	 * Fires the {@code BeforeFlowScopeCreated} event and propagates it to all
	 * registered flow lifecycle listeners.
	 */
	public void fireBeforeScopeDestroyedEvent(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore);
}
