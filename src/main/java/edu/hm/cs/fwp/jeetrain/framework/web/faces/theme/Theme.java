/* Theme.java @(#)%PID%
 */

package edu.hm.cs.fwp.jeetrain.framework.web.faces.theme;

import java.io.Serializable;

/**
 * Value class representing a theme that control the visual look-and-feel of an
 * application.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 27.03.2012 16:30:27
 */
public final class Theme implements Serializable {

	private static final long serialVersionUID = -331419523846368287L;

	private final String name;

	private final String libraryName;

	private final String templatesPath;

	private final String imagesPath;

	private final String stylesPath;

	private final String scriptsPath;

	public Theme(String name, String libraryName, String templatesPath, String imagesPath,
			String stylesPath, String scriptsPath) {
		this.name = name;
		this.libraryName = libraryName;
		this.templatesPath = templatesPath;
		this.imagesPath = imagesPath;
		this.stylesPath = stylesPath;
		this.scriptsPath = scriptsPath;
	}

	/**
	 * Returns the name of this theme.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the path to the facelets templates folder.
	 */
	public String getTemplatesPath() {
		return templatesPath;
	}

	/**
	 * Returns the name of the associated resource library.
	 */
	public String getLibraryName() {
		return libraryName;
	}

	/**
	 * Returns the relative path to the images folder within the resource
	 * library.
	 */
	public String getImagesPath() {
		return imagesPath;
	}

	/**
	 * Returns the relative path to the stylesheets folder within the resource
	 * library.
	 */
	public String getStylesPath() {
		return stylesPath;
	}

	/**
	 * Returns the relative path to the javascript folder within the resource
	 * library.
	 */
	public String getScriptsPath() {
		return scriptsPath;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + this.name.hashCode();
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = this == obj;
		if (!result && obj instanceof Theme) {
			Theme other = (Theme) obj;
			result = this.name.equals(other.name);
		}
		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}

}
