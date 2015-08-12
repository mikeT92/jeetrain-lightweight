/* FlowStack.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.io.Serializable;
import java.util.ArrayDeque;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;

/**
 * Simple Implementation of a flow stack which is stored in the current HTTP
 * session.
 * <p>
 * Internally this implementation is based on the {@link ArrayDeque} class which is
 * not thread-safe. A replacement with a more modern thread-safe implementation
 * should be considered.
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since 27.01.2006 12:59:54
 */
final class FlowStack implements Serializable {

	public static final String ATTRIBUTE_NAME = "x20.web.flow.stack";

	private static final long serialVersionUID = -3887591274633808158L;

	private ArrayDeque<Flow> stackImpl = new ArrayDeque<Flow>();

	public Flow getActiveFlow() {
		Flow result = null;
		if (!this.stackImpl.isEmpty()) {
			result = this.stackImpl.peek();
		}
		return result;
	}

	public void pushFlow(Flow flow) {
		this.stackImpl.push(flow);
	}

	public Flow popFlow() {
		Flow result = null;
		if (!this.stackImpl.isEmpty()) {
			result = this.stackImpl.pop();
		}
		return result;
	}

	public boolean containsFlow(String flowId) {
		for (Flow current : this.stackImpl) {
			if (flowId.equals(current.getId()))
				return true;
		}
		return false;
	}

	public int size() {
		return this.stackImpl.size();
	}

	public boolean isEmpty() {
		return this.stackImpl.isEmpty();
	}
}
