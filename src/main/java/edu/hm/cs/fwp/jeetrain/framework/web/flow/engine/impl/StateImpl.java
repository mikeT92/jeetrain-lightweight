/* StateImpl.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.Collections;
import java.util.List;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowEventProcessor;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Transition;

/**
 * Implementation of a {@link State}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
class StateImpl implements State, FlowEventProcessor {

	private final String name;

	private final String viewPath;

	private final Flow owner;

	private List<Transition> transitions = Collections.<Transition> emptyList();

	public StateImpl(String name, String viewPath, Flow owner) {
		this.name = name;
		this.viewPath = viewPath;
		this.owner = owner;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	/**
	 * Delegates the event processing to the transitions triggered by the given
	 * event.
	 * <p>
	 * If no appropriate transition can be found, the event is simply ignored.
	 * </p>
	 * <p>
	 * To handle event parameters attached to eventName using '?' and '&' as
	 * separators correctly, eventName has to start with the eventName of a
	 * transition owned by this state to trigger this particular transition.
	 * </p>
	 * 
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowEventProcessor#processEvent(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext,
	 *      java.lang.String)
	 */
	@Override
	public void processEvent(FlowContext flowContext, String eventName) {
		for (Transition current : this.transitions) {
			if (eventName.startsWith(current.getEventName())) {
				FlowEventProcessor processor = (FlowEventProcessor) current;
				processor.processEvent(flowContext, eventName);
				break;
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Flow getOwner() {
		return this.owner;
	}

	@Override
	public String getViewPath() {
		return this.viewPath;
	}

	@Override
	public List<Transition> getTransitions() {
		return this.transitions;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StateImpl { name=\"" + this.name + "\" }";
	}

}
