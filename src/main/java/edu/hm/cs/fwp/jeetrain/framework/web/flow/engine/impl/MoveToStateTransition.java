/* MoveToStateTransition.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Transition that moves from the current state to a specified state within the
 * same flow.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public final class MoveToStateTransition extends AbstractTransition {

	private final String targetStateName;

	protected MoveToStateTransition(String eventId, State owner, String targetStateName) {
		super(eventId, owner);
		this.targetStateName = targetStateName;
	}

	/**
	 * Moves the owning flow to the target state and requests a redirect to the
	 * view representing the target state.
	 * 
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl.AbstractTransition#processEvent(edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext,
	 *      java.lang.String)
	 */
	@Override
	public void processEvent(FlowContext flowContext, String eventName) {
		FlowImpl owningFlow = (FlowImpl) getOwner().getOwner();
		owningFlow.moveToState(targetStateName);

		ViewReference toViewReference =
				new ViewReference.Builder()
						.withContextPath(flowContext.getCurrentViewReference().getContextPath())
						.withInternalPath(owningFlow.getCurrentViewPath())
						.withParameter(Flow.FLOW_ID_PARAM_NAME, owningFlow.getId()).build();
		flowContext.redirectRequired(toViewReference);
		flowContext.consumeEvent(eventName);
	}
}
