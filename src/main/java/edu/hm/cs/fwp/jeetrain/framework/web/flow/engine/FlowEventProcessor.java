/* FlowEventProcessor.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

/**
 * Processor which is capable of processing flow events.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public interface FlowEventProcessor {

	/**
	 * Processes the specified event and updates the current flow's lifecyle.
	 * 
	 * @param flowContext
	 *            flow engine context
	 * @param eventName
	 *            flow event
	 */
	public void processEvent(FlowContext flowContext, String eventName);
}
