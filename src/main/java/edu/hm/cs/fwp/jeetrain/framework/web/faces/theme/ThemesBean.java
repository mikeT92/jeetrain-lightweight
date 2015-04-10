/* ThemesBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * JSF Managed Bean that handle theme management.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 27.03.2012 16:24:31
 */
@ManagedBean("themes")
@SessionScoped
public class ThemesBean implements Serializable {

	private static final long serialVersionUID = 5252485484727620L;

	private static final String DEFAULT_THEME_NAME = "blitzer";

	private static final Map<String, Theme> THEMES_BY_NAME;

	static {
		THEMES_BY_NAME = new LinkedHashMap<String, Theme>();
		THEMES_BY_NAME.put("blitzer", new Theme(
				"blitzer", "primefaces-blitzer", "/resources/primefaces-blitzer/layouts", "images",
				"styles", "scripts"));
		THEMES_BY_NAME.put("ui-darkness", new Theme(
				"blitzer", "primefaces-ui-darkness", "/resources/primefaces-ui-darkness/layouts",
				"images", "styles", "scripts"));
		THEMES_BY_NAME.put("ui-lightness", new Theme(
				"blitzer", "primefaces-ui-lightness", "/resources/primefaces-ui-lightness/layouts",
				"images", "styles", "scripts"));
		THEMES_BY_NAME.put("vader", new Theme(
				"blitzer", "primefaces-vader", "/resources/primefaces-vader/layouts", "images",
				"styles", "scripts"));
	}

	private Theme current;

	private Theme selected;

	private List<SelectItem> supportedThemes;

	/**
	 * Initializes the currently used theme according to the current user's
	 * preferences.
	 * 
	 * @throws IllegalStateException
	 *             if the configured theme is unknown.
	 */
	@PostConstruct
	public void onPostConstruct() {
		String initialThemeName = null;

		if (initialThemeName == null) {
			initialThemeName = DEFAULT_THEME_NAME;
		}

		this.current = THEMES_BY_NAME.get(initialThemeName);
		if (this.current == null) {
			throw new IllegalStateException("Unknown theme [" + initialThemeName + "]!");
		}

		this.supportedThemes = new ArrayList<SelectItem>(THEMES_BY_NAME.size());
		for (String current : THEMES_BY_NAME.keySet()) {
			this.supportedThemes.add(new SelectItem(current, current));
		}
	}

	/**
	 * Returns the currently used theme.
	 */
	public Theme getCurrent() {
		return this.current;
	}

	public void setSelected(String themeName) {
		this.selected = THEMES_BY_NAME.get(themeName);
	}

	public String getSelected() {
		return this.selected != null ? this.selected.getName() : this.current.getName();
	}

	/**
	 * Returns the list of supported themes to rendered in a combobox.
	 */
	public List<SelectItem> getSupportedThemes() {
		return this.supportedThemes;
	}

	public String switchTheme() {
		System.out.println("themes.switchTheme");
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		if (this.selected == null) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Please select one theme!", null));
			return null;
		}

		this.current = this.selected;
		this.selected = null;

		return facesContext.getViewRoot().getViewId() + "?faces-redirect=true";
	}
}
