/* SequenceBasedFlowIdGenerator.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of {@link FlowIdGenerator} based on {@link AtomicInteger}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 26.01.2012 09:37:16
 */
final class SequenceBasedFlowIdGenerator implements FlowIdGenerator {

	private final AtomicInteger nextFlowId = new AtomicInteger();
	
	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl.FlowIdGenerator#getNextFlowId()
	 */
	@Override
	public String getNextFlowId() {
		return Integer.toString(this.nextFlowId.incrementAndGet());
	}
}
