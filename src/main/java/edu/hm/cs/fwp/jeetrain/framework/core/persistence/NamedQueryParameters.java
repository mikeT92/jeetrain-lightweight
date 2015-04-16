/*
 * NamedQueryParameters.java 
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Query;

/**
 * Konkrete Implementierung von {@link QueryParameters}, die benannte Parameter unterstützt.
 * 
 * @author theism
 * @version 1.0
 * @since 16.04.2015
 */
public final class NamedQueryParameters implements QueryParameters {
    public static final class Builder {
        private final Map<String, Object> parametersByName = new LinkedHashMap<String, Object>();
        public Builder withParameter(String name, Object value) {
            this.parametersByName.put(name, value);
            return this;
        }
        public QueryParameters build() {
            return new NamedQueryParameters(parametersByName);
        }
    }
    private final Map<String, Object> parametersByName;
    private NamedQueryParameters(Map<String, Object> parametersByName) {
        this.parametersByName = parametersByName;
    }
    @Override
    public void applyParameters(Query query) {
        for (Map.Entry<String, Object> current : this.parametersByName.entrySet()) {
            query.setParameter(current.getKey(), current.getValue());
        }
    }
}
