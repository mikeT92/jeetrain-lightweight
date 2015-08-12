/* FlowLifecyleImpl.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycle;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycleListener;

/**
 * Default implementation of {@link FlowLifecycle}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.01.2012 16:48:06
 */
final class FlowLifecyleImpl implements FlowLifecycle {

	private static final Logger logger = LoggerFactory.getLogger(FlowLifecyleImpl.class);
	
	private final Set<FlowLifecycleListener> listeners = new HashSet<FlowLifecycleListener>();
	
	@Override
	public void registerListener(FlowLifecycleListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void unregisterListener(FlowLifecycleListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void fireAfterFlowScopeCreatedEvent(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore) {
		for (FlowLifecycleListener current : this.listeners) {
			try {
				current.afterFlowScopeCreated(flowContext, flowScopeBeanStore);
			} catch (Exception ex) {
				logger.error("*** FLOW *** Unable to propagate AfterFlowScopeCreatedEvent to listener [" + current + "]! Continue with next listener!", ex);
			}
		}
	}

	@Override
	public void fireBeforeScopeDestroyedEvent(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore) {
		for (FlowLifecycleListener current : this.listeners) {
			try {
				current.beforeFlowScopeDestroyed(flowContext, flowScopeBeanStore);
			} catch (Exception ex) {
				logger.error("*** FLOW *** Unable to propagate BeforeScopeDestroyedEvent to listener [" + current + "]! Continue with next listener!", ex);
			}
		}
	}

}
