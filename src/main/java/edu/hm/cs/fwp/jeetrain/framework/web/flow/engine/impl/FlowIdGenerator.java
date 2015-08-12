package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

/**
 * Flow ID generator which creates unique identifiers for executable flows. 
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
interface FlowIdGenerator {

	/**
	 * Returns a unique flow ID for an executable flow.
	 * @return unique flow ID
	 */
	public abstract String getNextFlowId();
}