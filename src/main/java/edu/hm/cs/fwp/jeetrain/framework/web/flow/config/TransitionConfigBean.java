/* TransitionConfigBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Meta information representing one {@code Transition} between states of a
 * flow.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 14:38:48
 */
public final class TransitionConfigBean {

	/**
	 * Supported transition types that the flow engine supports.
	 */
	public enum Type {
		/**
		 * Call a local or remote flow.
		 */
		callFlow,
		/**
		 * Continue with a local or remote flow.
		 */
		chainFlow,
		/**
		 * Exit the current flow possibly returning to calling local or remote
		 * flow.
		 */
		exitFlow,
		/**
		 * Move to a specific state within the same flow.
		 */
		moveToState
	}

	/**
	 * Transition type.
	 * <p>
	 * Defaults to {@code Type.moveToState}.
	 * </p>
	 */
	private Type type = Type.moveToState;

	/**
	 * Name of the event that triggers this transition.
	 */
	private String eventName;
	
	/**
	 * Target flow name of this transition.
	 */
	private String targetFlowName;

	/**
	 * Target state name of this transition.
	 */
	private String targetStateName;

	/**
	 * Target internal or external view path of this transition, if no flow is
	 * available.
	 */
	private String targetViewPath;

	/**
	 * List of parameters to be propagated to the target of this transition.
	 */
	private List<String> parameterNames = new ArrayList<String>();

//	/**
//	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
//	 */
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		switch (this.type) {
//		case callFlow:
//			Conditions.requirePropertyNotEmpty(this.targetFlowName, "targetFlowName");
//			Conditions.requireOptionalPropertyNotEmpty(this.targetStateName, "targetStateName");
//			break;
//		case chainFlow:
//			Conditions.requirePropertyNotEmpty(this.targetFlowName, "targetFlowName");
//			Conditions.requireOptionalPropertyNotEmpty(this.targetStateName, "targetStateName");
//			break;
//		case exitFlow:
//			break;
//		case moveToState:
//			Conditions.requirePropertyNotEmpty(this.targetStateName, "targetStateName");
//			break;
//		}
//		Conditions.requirePropertyNotEmpty(this.eventName, "eventName");
//	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getTargetFlowName() {
		return targetFlowName;
	}

	public void setTargetFlowName(String targetFlowName) {
		this.targetFlowName = targetFlowName;
	}

	public String getTargetStateName() {
		return targetStateName;
	}

	public void setTargetStateName(String targetStateName) {
		this.targetStateName = targetStateName;
	}

	public String getTargetViewPath() {
		return targetViewPath;
	}

	public void setTargetViewPath(String targetViewPath) {
		this.targetViewPath = targetViewPath;
	}

	public List<String> getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(List<String> parameterNames) {
		this.parameterNames = parameterNames;
	}

	public void setParameterName(String parameterName) {
		this.parameterNames.add(parameterName);
	}
}
