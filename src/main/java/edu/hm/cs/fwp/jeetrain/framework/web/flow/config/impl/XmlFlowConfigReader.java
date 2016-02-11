/**
 * 
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config.impl;

import java.util.List;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigBean;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigReader;

/**
 * {@code FlowConfigReader} implementation that reads all flow configurations 
 * define in XML format.
 * 
 * @author theism
 * @version 1.0
 * @since 20/08/2015
 */
public class XmlFlowConfigReader implements FlowConfigReader {

	private static final String FLOW_CONFIG_FILE_SUFFIX = "-flow";
	/* (non-Javadoc)
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.config.FlowConfigReader#readFlowConfigurations()
	 */
	@Override
	public List<FlowConfigBean> readFlowConfigurations() {
		// TODO Auto-generated method stub
		return null;
	}
}
