/* PageCriteria.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Bundles attributes that control the contents of paginated queries.
 * <p>
 * Right now, this value class only supports pagination as supported by JPA
 * using a {@code firstPosition} which defines the first element on the
 * resulting page and {@code pageSize} which determines the maximum number of
 * elements on the resulting page. Nevertheless, introducing a dedicated value
 * class for page criteria provides an excellent extension point for custom
 * pagination algorithms.
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
@XmlType(name = "PageCriteria", namespace = "http://persistence.core.framework.utrain.unicredit.eu")
@XmlAccessorType(XmlAccessType.FIELD)
public final class PageCriteria implements Serializable {

	private static final long serialVersionUID = -5051476904316260824L;

	private List<QueryParameter> queryParameters = Collections.emptyList();

	private int firstPosition;

	private int pageSize;

	public PageCriteria() {
	}

	/**
	 * Specialized constructor which provides a fully initialized instance.
	 * 
	 * @param queryParameters
	 *            query parameters to be passed to both queries
	 * @param firstPosition
	 *            zero-based index of the first element on a page
	 * @param pageSize
	 *            maximum number of elements on a page
	 */
	public PageCriteria(
			List<QueryParameter> queryParameters, int firstPosition, int pageSize) {
		if (queryParameters != null && !queryParameters.isEmpty())
			this.queryParameters = new ArrayList<QueryParameter>(queryParameters);
		this.firstPosition = firstPosition;
		this.pageSize = pageSize;
	}

	/**
	 * Returns the unmodifiable list of {@code QueryParameter}s to be passed to
	 * the query.
	 */
	public List<QueryParameter> getQueryParameters() {
		return this.queryParameters;
	}

	/**
	 * Returns the zero-based index of the first element to copied from the
	 * query result set.
	 */
	public int getFirstPosition() {
		return firstPosition;
	}

	/**
	 * Returns the number of elements that one page can hold.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + queryParameters.hashCode();
		result = prime * result + firstPosition;
		result = prime * result + pageSize;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageCriteria other = (PageCriteria) obj;
		if (!queryParameters.equals(other.queryParameters))
			return false;
		if (firstPosition != other.firstPosition)
			return false;
		if (pageSize != other.pageSize)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("PageCriteria { queryParameters : [ ");
		for (QueryParameter current : this.queryParameters) {
			result.append(current).append(" ");
		}
		result
				.append("], firstPosition : ").append(this.firstPosition).append(", pageSize : ")
				.append(this.pageSize).append(" }");
		return result.toString();
	}
}
