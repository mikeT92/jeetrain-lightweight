/* ExitFlowTransition.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.List;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Transition that exits the current flow and returns to any calling flow or
 * view.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 26.01.2012 10:34:55
 */
final class ExitFlowTransition extends AbstractTransition {

	private final List<String> parameterNames;

	protected ExitFlowTransition(String eventId, State owner, List<String> parameterNames) {
		super(eventId, owner);
		this.parameterNames = parameterNames;
	}

	@Override
	public void processEvent(FlowContext flowContext, String eventName) {

		flowContext.popFlow();
		Flow targetFlow = flowContext.getActiveFlow();
		if (targetFlow != null) {
			// we are returning to the local caller flow
			// FIXME: add propagation of parameters !!!

			ViewReference fromViewReference = flowContext.getCurrentViewReference();
			ViewReference toViewReference =
					new ViewReference.Builder()
							.withContextPath(fromViewReference.getContextPath())
							.withInternalPath(targetFlow.getCurrentViewPath())
							.withParameter(Flow.FLOW_ID_PARAM_NAME, targetFlow.getId())
							.build();
			flowContext.redirectRequired(toViewReference);

			if (this.logger.isDebugEnabled()) {
				this.logger.debug("*** FLOW *** processEvent: flow [" + getOwner().getOwner()
						+ "] state [" + getOwner() + "] request [" + fromViewReference
						+ "] event [" + eventName + "]: returning to caller flow [" + targetFlow
						+ "] initiates redirect to [" + toViewReference + "]...");
			}
		} else {
			// we are returning to the successor view determined during this
			// flow's
			// activation
			flowContext.redirectRequired(getOwner().getOwner().getReturnToViewReference());

			if (this.logger.isDebugEnabled()) {
				this.logger.debug("*** FLOW *** processEvent: flow [" + getOwner().getOwner()
						+ "] state [" + getOwner() + "] request ["
						+ flowContext.getCurrentViewReference() + "] event [" + eventName
						+ "]: returning to caller view initiates redirect to ["
						+ getOwner().getOwner().getReturnToViewReference() + "]...");
			}
		}
		flowContext.consumeEvent(eventName);
	}
}
