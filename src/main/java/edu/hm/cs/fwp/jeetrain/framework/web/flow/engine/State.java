/* State.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import java.util.List;

/**
 * State representing a business activity within a {@link Flow}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public interface State {

	/**
	 * Returns the human-readable name of this state.
	 */
	public String getName();

	/**
	 * Returns the {@link Flow} which owns this state.
	 */
	public Flow getOwner();

	/**
	 * Returns the internal view path that activates this state.
	 */
	public String getViewPath();

	/**
	 * Returns all transitions of this state as an unmodifiable list.
	 */
	public List<Transition> getTransitions();
}
