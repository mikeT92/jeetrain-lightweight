/* FlowConfigRepositoryBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigBean;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigRepository;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Spring based repository that collects {@link FlowConfigBean}s from Spring
 * application contexts using the BeanPostProcessor mechanism.
 * <p>
 * TODO: add validation of flow references!
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 15:18:15
 */
@Named("flowConfigRepository")
public final class FlowConfigRepositoryBean implements FlowConfigRepository {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<FlowConfigBean> flows = new ArrayList<FlowConfigBean>();

	/**
	 * Map of all collected flow definitions using the flow name as key.
	 */
	private Map<String, FlowConfigBean> flowsByName = new HashMap<String, FlowConfigBean>();

	/**
	 * Map of all collected flow definitions using the flows view path as key.
	 */
	private Map<String, FlowConfigBean> flowsByPath = new HashMap<String, FlowConfigBean>();

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigRepository#getFlowConfigByFlowName(java.lang.String)
	 */
	@Override
	public FlowConfigBean getFlowConfigByFlowName(String flowName) {
		return this.flowsByName.get(flowName);
	}

	@Override
	public FlowConfigBean getFlowConfigByViewPath(String viewPath) {
		return this.flowsByPath.get(viewPath);
	}

	@Override
	public FlowConfigBean getFlowConfigByViewReference(ViewReference viewReference) {
		return this.flowsByPath.get(viewReference.getInternalPath());
	}
//
//	/**
//	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object,
//	 *      java.lang.String)
//	 */
//	@Override
//	public Object postProcessAfterInitialization(Object bean, String beanName)
//			throws BeansException {
//		if (bean instanceof FlowConfigBean) {
//			FlowConfigBean flowConfigBean = (FlowConfigBean) bean;
//			this.flows.add(flowConfigBean);
//			this.flowsByName.put(flowConfigBean.getName(), flowConfigBean);
//			this.logger.info(
//					"*** FLOW *** added flow definition [{}] to repository...",
//					flowConfigBean.getName());
//			List<String> viewPaths = flowConfigBean.getViewPaths();
//			for (String currentViewPath : viewPaths) {
//				this.flowsByPath.put(currentViewPath, flowConfigBean);
//				this.logger.debug(
//						"*** FLOW *** added view path [{}] link to flow [{}]...", currentViewPath,
//						flowConfigBean.getName());
//			}
//		}
//		return bean;
//	}

	/**
	 * Returns all collected flow definitions as unmodifiable list.
	 */
	public List<FlowConfigBean> getAllFlows() {
		return Collections.unmodifiableList(this.flows);
	}
}
