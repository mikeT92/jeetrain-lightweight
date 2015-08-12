/* CallLocalFlowTransition.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.QueryParametersUtil;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Transition that calls a local flow when triggered.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public final class CallLocalFlowTransition extends AbstractTransition {

	private final String targetFlowName;

	private final String targetStateName;

	private final List<String> parameterNames;

	public CallLocalFlowTransition(String eventId, State owner, String targetFlowName,
			String targetStateName, List<String> parameterNames) {
		super(eventId, owner);
		this.targetFlowName = targetFlowName;
		this.targetStateName = targetStateName;
		this.parameterNames =
				parameterNames != null ? parameterNames : Collections.<String> emptyList();
	}

	@Override
	public void processEvent(FlowContext flowContext, String eventName) {
		Flow targetFlow = flowContext.getFlowFactory().createFlow(this.targetFlowName);
		if (targetFlow == null) {
			throw new IllegalStateException("Unable to create target flow [" + this.targetFlowName
					+ "]! Are you sure that you configured a flow with that name?");
		}

		targetFlow.activate(flowContext, this.targetStateName, null);

		Map<String, String> eventParameters = QueryParametersUtil.split(eventName);
		ViewReference fromViewReference = flowContext.getCurrentViewReference();
		ViewReference.Builder builder =
				new ViewReference.Builder()
						.withContextPath(fromViewReference.getContextPath())
						.withInternalPath(targetFlow.getCurrentViewPath());
		if (eventParameters != null) {
			builder.withParameters(eventParameters);
		}
		builder.withParameter(Flow.FLOW_ID_PARAM_NAME, targetFlow.getId()).build();
		ViewReference toViewReference = builder.build();
		flowContext.redirectRequired(toViewReference);
		flowContext.consumeEvent(eventName);
		flowContext.pushFlow(targetFlow);

		if (this.logger.isDebugEnabled()) {
			this.logger.debug("*** FLOW *** processEvent: flow [" + getOwner().getOwner()
					+ "] state [" + getOwner() + "] request [" + fromViewReference + "] event ["
					+ eventName + "]: calling flow [" + targetFlow + "] causes redirect to ["
					+ toViewReference + "]...");
		}
	}
}
