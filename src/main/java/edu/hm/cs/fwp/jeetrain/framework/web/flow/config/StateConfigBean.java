/* StateConfigBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Meta information representing one {@code State} within a {@code Flow}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 14:34:05
 */
public final class StateConfigBean {

	/**
	 * Name of this state, must be unique within a flow.
	 * <p>
	 * Defaults to {@link StateConfigBean#beanName}.
	 * </p>
	 */
	private String name;

	/**
	 * Unique Spring bean ID.
	 */
	private String beanName;

	/**
	 * Internal view path that represents this state.
	 * <p>
	 * Please note that it is not mandatory to assign a view path to a state since
	 * the flow engine is capable of tracking the current state of a particular
	 * flow using the state name only.
	 * </p>
	 */
	private String viewPath;

	/**
	 * List of internal view IDs that trigger the activation of this flow.
	 */
	private List<TransitionConfigBean> transitions = new ArrayList<TransitionConfigBean>();

//	/**
//	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
//	 */
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		if (this.name == null) {
//			this.name = beanName;
//		}
//		Conditions.requirePropertyNotEmpty(this.name, "name");
//		// FIXME: add Conditions.requirePropertyNotEmpty(this.transitions, "transitions");
//	}

	public void setBeanName(String name) {
		this.beanName = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewId) {
		this.viewPath = viewId;
	}

	public List<TransitionConfigBean> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<TransitionConfigBean> transitions) {
		this.transitions = transitions;
	}
}
