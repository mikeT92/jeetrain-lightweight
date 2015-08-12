/* FlowFactoryBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigBean;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigRepository;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.StateConfigBean;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.TransitionConfigBean;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Transition;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Implementation of a {@link FlowFactory} based on a Spring managed POJO.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
@Named("flowFactory")
public final class FlowFactoryBean implements FlowFactory {

	@Inject
	private FlowConfigRepository flowConfigRepository;

	// FIXME: autowire to enable different flow ID generator implementations
	private FlowIdGenerator flowIdGenerator = new SequenceBasedFlowIdGenerator();

	@Override
	public Flow createFlow(String flowName) {
		Flow result = null;
		FlowConfigBean flowConfig = this.flowConfigRepository.getFlowConfigByFlowName(flowName);
		if (flowConfig != null) {
			result = createFlow(flowConfig);
		}
		return result;
	}

	@Override
	public Flow createFlowFromViewPath(String viewPath) {
		Flow result = null;
		FlowConfigBean flowConfig = this.flowConfigRepository.getFlowConfigByViewPath(viewPath);
		if (flowConfig != null) {
			result = createFlow(flowConfig);
		}
		return result;
	}

	@Override
	public Flow createFlowFromViewReference(ViewReference viewReference) {
		Flow result = null;
		FlowConfigBean flowConfig =
				this.flowConfigRepository.getFlowConfigByViewReference(viewReference);
		if (flowConfig != null) {
			result = createFlow(flowConfig);
		}
		return result;
	}

	private Flow createFlow(FlowConfigBean definition) {
		FlowImpl newFlow = new FlowImpl(this.flowIdGenerator.getNextFlowId(), definition.getName());
		List<State> newStates = new ArrayList<State>(definition.getStates().size());
		for (StateConfigBean currentStateConfig : definition.getStates()) {
			newStates.add(createState(currentStateConfig, newFlow));
		}
		newFlow.setStates(newStates);
		return newFlow;
	}

	private State createState(StateConfigBean definition, Flow ownerFlow) {
		StateImpl newState =
				new StateImpl(definition.getName(), definition.getViewPath(), ownerFlow);
		List<Transition> newTransitions =
				new ArrayList<Transition>(definition.getTransitions().size());
		for (TransitionConfigBean currentTransitionConfig : definition.getTransitions()) {
			newTransitions.add(createTransition(currentTransitionConfig, ownerFlow, newState));
		}
		newState.setTransitions(newTransitions);
		return newState;
	}

	private Transition createTransition(TransitionConfigBean definition, Flow ownerFlow,
			State ownerState) {
		AbstractTransition newTransition = null;
		switch (definition.getType()) {
		case moveToState:
			newTransition =
					new MoveToStateTransition(
							definition.getEventName(), ownerState, definition.getTargetStateName());
			break;
		case callFlow:
			newTransition =
					new CallLocalFlowTransition(
							definition.getEventName(), ownerState, definition.getTargetFlowName(),
							definition.getTargetStateName(), definition.getParameterNames());
			break;
		case chainFlow:
			newTransition =
					new ChainLocalFlowTransition(
							definition.getEventName(), ownerState, definition.getTargetFlowName(),
							definition.getTargetStateName(), definition.getParameterNames());
			break;
		case exitFlow:
			newTransition =
					new ExitFlowTransition(
							definition.getEventName(), ownerState, definition.getParameterNames());
			break;
		default:
			throw new IllegalArgumentException("Unsupported transition type ["
					+ definition.getType() + "]!");
		}
		return newTransition;
	}
}
