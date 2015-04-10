/* ClassPathAwareResourceResolver.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.facelets;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.facelets.ResourceResolver;

/**
 * Facelets ResourceResolver implementation that additionally can load facelet
 * templates located in JAR files.
 * <p>
 * Please add a servlet context parameter named
 * <code>javax.faces.FACELETS_RESOURCE_RESOLVER</code> specifying the fully
 * qualified name of this class as value in order to use this implementation.
 * </p>
 * <p>
 * Facelet templates stored in JARs are expected to be located in folder
 * <code>/META-INF/resources</code>. If the specified resource path does not
 * start with this prefix, it will be added by default.
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public final class ClassPathAwareResourceResolver extends ResourceResolver {

	private final Logger logger = Logger.getLogger(getClass().getName());

	private final ResourceResolver decorated;

	/**
	 * Constructs an instance that decorates an existing
	 * <code>ResourceResolver</code> object.
	 */
	public ClassPathAwareResourceResolver(ResourceResolver decorated) {
		this.decorated = decorated;
		this.logger.info("Decorating existing resource resolver [" + decorated + "]");
	}

	@Override
	public URL resolveUrl(String path) {
		URL result = this.decorated.resolveUrl(path);
		if (result == null) {
			String canonicalPath = path;
			if (canonicalPath.startsWith("/")) {
				canonicalPath = canonicalPath.substring(1);
			}
			if (!canonicalPath.startsWith("META-INF/resources")) {
				if (!canonicalPath.startsWith("resources")) {
					canonicalPath = "resources/" + canonicalPath;
				}
				if (!canonicalPath.startsWith("META-INF")) {
					canonicalPath = "META-INF/" + canonicalPath;
				}
			}
			result = Thread.currentThread().getContextClassLoader().getResource(canonicalPath);
			if (this.logger.isLoggable(Level.FINE)) {
				this.logger.fine("Resolved resource path [" + path + "] to URL [" + result
						+ "] using canonical path [" + canonicalPath + "]");
			}
		}
		return result;
	}
}
