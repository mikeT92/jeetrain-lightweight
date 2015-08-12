/* ViewReference.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.view;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Immutable value class that represents relative or absolute URIs of views.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 08.02.2006 10:08:45
 */
public final class ViewReference implements Serializable {

	private static final long serialVersionUID = -488622565630791743L;

	/**
	 * Builder for {@code ViewReference}s.
	 */
	public static final class Builder {

		private String scheme;

		private String authority;

		private String host;

		private int port = -1;

		private String path;

		private String contextPath;

		private String internalPath;

		private String query;

		private Map<String, String> parameters;

		private String uri;

		private URI impl;

		public Builder withScheme(String scheme) {
			this.scheme = scheme;
			return this;
		}

		public Builder withAuthority(String authority) {
			this.authority = authority;
			return this;
		}

		public Builder withHost(String host) {
			this.host = host;
			return this;
		}

		public Builder withPort(int port) {
			this.port = port;
			return this;
		}

		public Builder withPath(String path) {
			this.path = path;
			return this;
		}

		public Builder withContextPath(String contextPath) {
			this.contextPath = normalizePathComponent(contextPath);
			return this;
		}

		public Builder withInternalPath(String internalPath) {
			this.internalPath = normalizePathComponent(internalPath);
			return this;
		}

		public Builder withQuery(String query) {
			this.query = query;
			return this;
		}

		public Builder withParameter(String name, String value) {
			if (this.parameters == null) {
				this.parameters = new LinkedHashMap<String, String>();
			}
			this.parameters.put(name, value);
			return this;
		}

		public Builder withParameters(Map<String, String> parameters) {
			if (this.parameters == null) {
				this.parameters = new LinkedHashMap<String, String>();
			}
			this.parameters.putAll(parameters);
			return this;
		}

		public Builder withUriString(String uri) {
			this.uri = uri;
			return this;
		}

		public ViewReference build() {
			if (this.impl != null) {
				return new ViewReference(this.impl);
			}
			if (this.uri != null) {
				return buildFromUriString();
			}
			joinPathIfNecessary();
			joinQueryIfNecessary();
			if (this.authority != null) {
				return buildFromPartsUsingAuthority();
			}
			return buildFromParts();
		}

		private void joinPathIfNecessary() {
			if (this.contextPath != null || this.internalPath != null) {
				StringBuilder pathBuilder = new StringBuilder();
				if (this.contextPath != null) {
					pathBuilder.append(this.contextPath);
				}
				if (this.internalPath != null) {
					pathBuilder.append(this.internalPath);
				}
				this.path = pathBuilder.toString();
			}
		}

		private void joinQueryIfNecessary() {
			if (this.parameters != null) {
				StringBuilder queryBuilder = new StringBuilder();
				if (this.query != null) {
					queryBuilder.append(this.query);
				}
				boolean first = this.query == null;
				for (Map.Entry<String, String> parameter : this.parameters.entrySet()) {
					if (!first) {
						queryBuilder.append('&');
					}
					first = false;
					queryBuilder.append(parameter.getKey());
					queryBuilder.append('=');
					queryBuilder.append(parameter.getValue());
				}
				this.query = queryBuilder.toString();
			}
		}

		private ViewReference buildFromUriString() {
			try {
				this.impl = new URI(uri);
			} catch (URISyntaxException ex) {
				throw new IllegalArgumentException(
						"Unable to build view reference from URI string [" + this.uri + "]: "
								+ ex.getMessage(), ex);
			}
			return new ViewReference(this.impl);
		}

		private ViewReference buildFromPartsUsingAuthority() {
			try {
				this.impl = new URI(this.scheme, this.authority, this.path, this.query, null);
			} catch (URISyntaxException ex) {
				throw new IllegalArgumentException(ex);
			}
			return new ViewReference(this.impl);
		}

		private ViewReference buildFromParts() {
			try {
				this.impl =
						new URI(
								this.scheme, null, this.host, this.port, this.path, this.query,
								null);
			} catch (URISyntaxException ex) {
				throw new IllegalArgumentException(ex);
			}
			return new ViewReference(this.impl);
		}

		private static String normalizePathComponent(String pathComponent) {
			String result = pathComponent;
			if (result.endsWith("/")) {
				result = result.substring(0, result.length() - 1);
			}
			if (!result.startsWith("/")) {
				result = "/" + result;
			}
			return result;
		}
	}

	/**
	 * Regular expression used to recognize context path part in path elements.
	 */
	private static final Pattern CONTEXT_PATH_PATTERN = Pattern.compile("^/.*-PF/.*");

	/**
	 * Path component which identifies the web application.
	 */
	private String contextPath;

	/**
	 * Path component which identifies a particular view within a web
	 * application.
	 */
	private String internalPath;

	/**
	 * Suffix of last path component used for suffix based URL mapping to
	 * servlets/filters.
	 */
	private String pathSuffix;

	/**
	 * Internal representation of a view reference.
	 */
	private final URI impl;

	private ViewReference(URI uri) {
		this.impl = uri;
		splitPathComponents();
	}

	/**
	 * Returns the scheme component of this view reference, if this instance
	 * represents an absolute URI; <code>null</code> otherwise.
	 */
	public String getScheme() {
		return this.impl.getScheme();
	}

	/**
	 * Returns the authority of this view reference, if an authority has been
	 * specified when this instance was created; <code>null</code> otherwise.
	 */
	public String getAuthority() {
		return this.impl.getAuthority();
	}

	/**
	 * Returns the host component of this view reference, if this instance
	 * represents an absolute URI; <code>null</code> otherwise.
	 */
	public String getHost() {
		return this.impl.getHost();
	}

	/**
	 * Returns the port component of this view reference, if this instance
	 * represents an absolute URI and a port number were specified when
	 * constructing this instance; <code>-1/code> otherwise.
	 */
	public int getPort() {
		return this.impl.getPort();
	}

	/**
	 * Returns the view reference base (i.e. scheme + authority) of this view
	 * reference, if this instance represents an absolute URI; <code>null</code>
	 * otherwise.
	 */
	public ViewReference getBase() {
		ViewReference result = null;
		if (isAbsolute()) {
			result = new Builder().withScheme(getScheme()).withAuthority(getAuthority()).build();
		}
		return result;
	}

	/**
	 * Returns the decoded path component of this view reference.
	 */
	public String getPath() {
		return this.impl.getPath();
	}

	/**
	 * Return <code>true</code>, if this view reference represent an external
	 * URI which can be used outside the current application; <code>false</code>
	 * otherwise.
	 */
	public boolean isExternal() {
		return getContextPath() != null;
	}

	/**
	 * Returns the decoded path component that represents the context path of
	 * this view reference.
	 * <p>
	 * The context path is the first part of the path identifying a particular
	 * web application.
	 * </p>
	 * <p>
	 * Sample: if {@code path} is set to "/XA-XXX-PF/home/home.jsf" the
	 * {@code contextPath} is set to "/XA-XXX-PF".
	 * </p>
	 * 
	 * @return context path, if this view reference has a path;
	 *         <code>null</code> otherwise.
	 */
	public String getContextPath() {
		return this.contextPath;
	}

	/**
	 * Returns the decoded path component that represents the internal path of
	 * this view reference.
	 * <p>
	 * The internal path is the second part of the path identifying a particular
	 * view within a web application.
	 * </p>
	 * <p>
	 * Sample: if {@code path} is set to "/XA-XXX-PF/home/home.jsf" the
	 * {@code internalPath} is set to "/home/home.jsf".
	 * </p>
	 * 
	 * @return internal path, if this view reference has an internal path;
	 *         <code>null</code> otherwise.
	 */
	public String getInternalPath() {
		return this.internalPath;
	}

	/**
	 * Returns the decoded path component that represents the suffix of the
	 * path.
	 * <p>
	 * Sample: if {@code path} is set to "/XA-XXX-PF/home/home.jsf" the
	 * {@code pathSuffix} is set to ".jsf".
	 * </p>
	 * 
	 * @return path suffix, if the path ends with a suffix; <code>null</code>
	 *         otherwise.
	 */
	public String getPathSuffix() {
		return this.pathSuffix;
	}

	/**
	 * Returns the decoded query component of this view reference.
	 */
	public String getQuery() {
		return this.impl.getQuery();
	}

	/**
	 * Returns <code>true</code> if this view references represents an absolute
	 * URI; <code>false</code> otherwise.
	 */
	public boolean isAbsolute() {
		return this.impl.isAbsolute();
	}

	/**
	 * Returns a new view reference representing the relative part of this view
	 * reference.
	 */
	public ViewReference relativize() {
		ViewReference result = null;
		if (isAbsolute()) {
			result = new Builder().withPath(getPath()).withQuery(getQuery()).build();
		} else {
			result = this;
		}
		return result;
	}

	/**
	 * Returns a new view reference relatived according to the specified base.
	 */
	public ViewReference relativize(ViewReference base) {
		return new ViewReference(this.impl.relativize(base.impl));
	}

	/**
	 * Returns a new view reference absolutized according to the specified base.
	 */
	public ViewReference absolutize(ViewReference base) {
		return new ViewReference(base.impl.resolve(this.impl));
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.impl.hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object rhs) {
		boolean result = false;
		if (null != rhs && rhs instanceof ViewReference) {
			ViewReference typed = (ViewReference) rhs;
			result = this.impl.equals(typed.impl);
		}
		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.impl.toString();
	}

	private void splitPathComponents() {
		String path = this.impl.getPath();
		if (path != null) {
			if (isPathStartingWithContextPath()) {
				int endOfContextPath = path.indexOf('/', 1);
				if (endOfContextPath > 1) {
					this.contextPath = path.substring(0, endOfContextPath);
					this.internalPath = path.substring(endOfContextPath);
				}
			} else {
				this.contextPath = null;
				this.internalPath = path;
			}
			int startOfPathSuffix = path.lastIndexOf('.');
			if (startOfPathSuffix != -1) {
				this.pathSuffix = path.substring(startOfPathSuffix);
			}
		}
	}

	/**
	 * Returns <code>true</code>, if the path of the inner URI starts with a
	 * context path; <code>false</code> otherwise.
	 * <p>
	 * A context path is assumed to be present when a path has been specified
	 * and one of the following two conditions matches:
	 * </p>
	 * <ul>
	 * <li>the inner URI has an authority.</li>
	 * <li>the first component of the path ends with "-PF".</li>
	 * </ul>
	 */
	private boolean isPathStartingWithContextPath() {
		return this.impl.getPath() != null
				&& (this.impl.getAuthority() != null || CONTEXT_PATH_PATTERN.matcher(
						this.impl.getPath()).matches());
	}
}
