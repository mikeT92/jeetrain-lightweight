/* FlowConfigReader.java
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config;

import java.util.List;

/**
 * Reader that collects flow configurations defined in a specific format.
 * @author theism
 * @version 1.0
 * @since 20/08/2015s
 */
public interface FlowConfigReader {

	/**
	 * Reads all flow configurations visible to this flow configuration reader.
	 * @return all flow configurations detected
	 */
	List<FlowConfigBean> readFlowConfigurations();
}
