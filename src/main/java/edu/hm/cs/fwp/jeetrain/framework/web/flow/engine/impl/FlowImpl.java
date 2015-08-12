/* FlowImpl.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowEventProcessor;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Implementation of an executable {@link Flow}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
final class FlowImpl implements Flow, FlowEventProcessor {

	private final String id;

	private final String name;

	private List<State> states = Collections.<State> emptyList();

	private State currentState;

	private ViewReference returnToViewReference;

	public FlowImpl(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	/**
	 * Delegates the event processing to the current state.
	 * 
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowEventProcessor#processEvent(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext,
	 *      java.lang.String)
	 */
	@Override
	public void processEvent(FlowContext flowContext, String eventName) {
		FlowEventProcessor processor = (FlowEventProcessor) getCurrentState();
		processor.processEvent(flowContext, eventName);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<State> getStates() {
		return this.states;
	}

	@Override
	public State getCurrentState() {
		return this.currentState;
	}

	/**
	 * Returns the view path of the current state.
	 * 
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow#getCurrentViewPath()
	 */
	@Override
	public String getCurrentViewPath() {
		return getCurrentState().getViewPath();
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow#getReturnToViewReference()
	 */
	@Override
	public ViewReference getReturnToViewReference() {
		return this.returnToViewReference;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow#activate(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext,
	 *      java.lang.String, java.util.Map)
	 */
	@Override
	public void activate(FlowContext flowContext, String firstStateName,
			Map<String, Object> parameters) {
		// initialize the current state
		this.currentState = this.states.get(0);
		if (firstStateName != null) {
			this.currentState = getRequiredStateByName(firstStateName);
		}
		// determine the return-to view reference
		if (this.returnToViewReference == null) {
			String referer = flowContext.getRequest().getHeader("Referer");
			if (StringUtils.isNotEmpty(referer)) {
				this.returnToViewReference =
						new ViewReference.Builder().withUriString(referer).build();
			} else {
				this.returnToViewReference =
						new ViewReference.Builder().withContextPath(
								flowContext.getCurrentViewReference().getContextPath()).build();
			}
		}
		// FIXME: add propagation of parameters!!!
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow#deactivate(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext)
	 */
	@Override
	public void deactivate(FlowContext flowContext) {
	}

	public void moveToState(String nextStateName) {
		this.currentState = getRequiredStateByName(nextStateName);
	}

	@Override
	public String toString() {
		return "FlowImpl { id=\"" + this.id + "\", name=\"" + this.name + "\" }";
	}

	private State getStateByName(String stateName) {
		State result = null;
		for (State current : this.states) {
			if (current.getName().equals(stateName)) {
				result = current;
				break;
			}
		}
		return result;
	}

	private State getRequiredStateByName(String stateName) {
		State result = getStateByName(stateName);
		if (result == null) {
			throw new IllegalArgumentException("Flow with flow ID [" + this.id + "] named ["
					+ this.name + "] does not have a state named [" + stateName + "]!");
		}
		return result;
	}
}
