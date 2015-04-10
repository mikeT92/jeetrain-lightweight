/* QueryParametersBuilder.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code Builder} implementation that helps to create lists of
 * {@code QueryParameter}s.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 12.03.2012 13:20:40
 */
public final class QueryParametersBuilder {

	private final List<QueryParameter> parameters;

	/**
	 * Constructs a new builder instance creating a parameter list using an
	 * initial default size.
	 */
	public QueryParametersBuilder() {
		this.parameters = new ArrayList<QueryParameter>();
	}

	/**
	 * Constructs a new builder instance starting with a parameter list that is
	 * large enough to hold the given number of parameters.
	 * <p>
	 * Of course, the parameter list is capable of holding more parameters than
	 * specified here.
	 * </p>
	 * 
	 * @param numberOfParameters
	 *            expected number of parameters
	 */
	public QueryParametersBuilder(int numberOfParameters) {
		this.parameters = new ArrayList<QueryParameter>(numberOfParameters);
	}

	/**
	 * Adds the parameter with the given name and value to the parameter list.
	 * @param name parameter name; must not be empty.
	 * @param value parameter value; must not be {@code null}
	 * @return this builder instance to enable contatenation of method calls.
	 */
	public QueryParametersBuilder withParameter(String name, Object value) {
		this.parameters.add(new QueryParameter(name, value));
		return this;
	}

	/**
	 * Returns the unmodifiable parameter list.
	 */
	public List<QueryParameter> build() {
		return Collections.unmodifiableList(this.parameters);
	}
}
