/* Transition.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

/**
 * Transition representing a state transition from one state of a flow to
 * another state of the same or a different flow.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public interface Transition {

	/**
	 * Returns the name of the event that triggers this transition.
	 */
	public String getEventName();
	
	/**
	 * Returns the state that owns this transaction.
	 */
	public State getOwner();
}
