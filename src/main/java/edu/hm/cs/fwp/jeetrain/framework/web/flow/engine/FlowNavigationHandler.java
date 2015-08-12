/* FlowNavigationHandler.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

/**
 * Flow-aware navigation handler which propagates events to the underlying flow
 * engine.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 26.01.2012 15:07:03
 */
public interface FlowNavigationHandler {

	/**
	 * Propagates the given event to the flow engine which will perform state
	 * changes on the currently active flow.
	 * 
	 * @param flowContext
	 *            current flow context
	 * @param response
	 *            current HTTP response
	 * @param eventName
	 *            navigation event name
	 */
	public void handleNavigation(FlowContext flowContext, String eventName);
}
