/* AbstractTransition.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowEventProcessor;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.State;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Transition;

/**
 * Abstract implementation of a transition to be used by all concrete transition implementations.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 18:31:02
 */
class AbstractTransition implements Transition, FlowEventProcessor {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String eventName;

	private final State owner;
	
	protected AbstractTransition(String eventName, State owner) {
		this.eventName = eventName;
		this.owner = owner;
	}
	
	@Override
	public String getEventName() {
		return this.eventName;
	}

	@Override
	public State getOwner() {
		return this.owner;
	}

	@Override
	public void processEvent(FlowContext flowContext, String eventName) {
	}
}
