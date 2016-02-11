/* FlowConfigBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Meta information representing one {@code Flow}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 13:55:09
 */
@XmlRootElement(name = "flow")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlowConfigBean {

	/**
	 * Human readable name of this flow.
	 * <p>
	 * Defaults to {@link FlowConfigBean#beanName}.
	 * </p>
	 */
	private String name;

	/**
	 * List of internal view paths that trigger the activation of this flow.
	 */
	private List<String> viewPaths = new ArrayList<String>();

	/**
	 * Controls the usage of view IDs when sending redirects to the client:
	 * <ul>
	 * <li><code>false</code> means that the view ID that triggered the
	 * activation of the flow is always used as redirect URLs regardless which
	 * view ID the current state has.</li>
	 * <li><code>true</code> means that the view ID of the current state is used
	 * as redirect URL.</li>
	 * </ul>
	 * <p>
	 * Defaults to <code>true</code>.
	 */
	private boolean usingStateViewIdOnRedirect = true;

	private List<StateConfigBean> states = new ArrayList<StateConfigBean>();

	// /**
	// * @see
	// org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	// */
	// @Override
	// public void afterPropertiesSet() throws Exception {
	// if (this.name == null) {
	// this.name = beanName;
	// }
	// Conditions.requirePropertyNotEmpty(this.name, "name");
	// // FIXME: add Conditions.requirePropertyNotEmpty(this.states, "states");
	// for (StateConfigBean currentState : this.states) {
	// if (currentState.getViewPath() != null) {
	// this.viewPaths.add(currentState.getViewPath());
	// }
	// }
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getViewPaths() {
		return viewPaths;
	}

	public void setViewPaths(List<String> viewIds) {
		this.viewPaths = viewIds;
	}

	public boolean isUsingStateViewIdOnRedirect() {
		return usingStateViewIdOnRedirect;
	}

	public void setUsingStateViewIdOnRedirect(boolean usingStateViewIdOnRedirect) {
		this.usingStateViewIdOnRedirect = usingStateViewIdOnRedirect;
	}

	public List<StateConfigBean> getStates() {
		return states;
	}

	public void setStates(List<StateConfigBean> states) {
		this.states = states;
	}
}
