/* FlowScopeEventAdapter.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.faces;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructCustomScopeEvent;
import javax.faces.event.PreDestroyCustomScopeEvent;
import javax.faces.event.ScopeContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycleListener;

/**
 * Faces-specific implementation of a {@link FlowLifecycleListener} which
 * propagates flow engine events as JSF system events in order to support
 * flow-scoped managed beans.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.01.2012 14:22:40
 */
final class FlowScopeEventAdapter implements FlowLifecycleListener {

	public static final String FLOW_SCOPE_NAME = "flowScope";

	private static final Logger logger = LoggerFactory.getLogger(FlowScopeEventAdapter.class);
	
	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycleListener#afterFlowScopeCreated(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext, java.util.Map)
	 */
	@Override
	public void afterFlowScopeCreated(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore) {
		logger.trace("*** FLOW *** publishing PostConstructCustomScopeEvent for flowScope...");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ScopeContext scopeContext = new ScopeContext(FLOW_SCOPE_NAME, flowScopeBeanStore);
		PostConstructCustomScopeEvent scopeEvent = new PostConstructCustomScopeEvent(scopeContext);
		application.publishEvent(facesContext, scopeEvent.getClass(), scopeEvent);
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowLifecycleListener#beforeFlowScopeDestroyed(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext, java.util.Map)
	 */
	@Override
	public void beforeFlowScopeDestroyed(FlowContext flowContext,
			Map<String, Object> flowScopeBeanStore) {
		logger.trace("*** FLOW *** publishing PreDestroyCustomScopeEvent for flowScope...");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ScopeContext scopeContext = new ScopeContext(FLOW_SCOPE_NAME, flowScopeBeanStore);
		PreDestroyCustomScopeEvent scopeEvent = new PreDestroyCustomScopeEvent(scopeContext);
		application.publishEvent(facesContext, scopeEvent.getClass(), scopeEvent);
	}
}
