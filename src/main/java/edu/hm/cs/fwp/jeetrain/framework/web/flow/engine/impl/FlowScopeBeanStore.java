/* FlowScopeBeanStore.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic bean store for all flow-scoped beans.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.01.2012 13:53:05
 */
public final class FlowScopeBeanStore implements Map<String, Object> {

	/**
	 * HTTP session attribute name.
	 */
	public static final String ATTRIBUTE_NAME = "x20.web.flow.beanStore";

	static final private Logger logger = LoggerFactory.getLogger(FlowScopeBeanStore.class);

	private ConcurrentHashMap<String, Object> delegate = new ConcurrentHashMap<String, Object>();

	@Override
	public void clear() {
		if (logger.isDebugEnabled()) {
			StringBuilder msgBuilder = new StringBuilder();
			msgBuilder.append("*** FLOW ***");
			msgBuilder.append(" discarding [");
			msgBuilder.append(this.delegate.size());
			msgBuilder.append("] beans in the flow-scoped bean store");
			if (!this.delegate.isEmpty()) {
				msgBuilder.append(":");
				for (Map.Entry<String, Object> current : this.delegate.entrySet()) {
					msgBuilder.append(" name=[");
					msgBuilder.append(current.getKey());
					msgBuilder.append("] type=[");
					msgBuilder.append(current.getValue());
					msgBuilder.append("]");
				}
			}
			msgBuilder.append(".");
			logger.debug(msgBuilder.toString());
		}
		this.delegate.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.delegate.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return this.delegate.entrySet();
	}

	@Override
	public Object get(Object key) {
		Object result = this.delegate.get(key);
		if (result != null) {
			logger.trace(
					"*** FLOW *** getting bean [{}] named [{}] from the flow-scoped bean store",
					result, key);
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return this.delegate.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return this.delegate.keySet();
	}

	@Override
	public Object put(String key, Object value) {
		logger.debug(
				"*** FLOW *** adding bean [{}] named [{}] to the flow-scoped bean store", value,
				key);
		return this.delegate.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		this.delegate.putAll(map);
	}

	@Override
	public Object remove(Object key) {
		Object result = this.delegate.remove(key);
		if (result != null) {
			logger.debug(
					"*** FLOW *** removing bean [{}] named [{}] from the flow-scoped bean store",
					result, key);
		}
		return result;
	}

	@Override
	public int size() {
		return this.delegate.size();
	}

	@Override
	public Collection<Object> values() {
		return this.delegate.values();
	}
}
