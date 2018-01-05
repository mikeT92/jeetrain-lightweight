/* ThemesBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSF Managed Bean that handles theme management.
 * 
 * @author mtheis
 * @version 1.0.0
 * @since release 1.0 27.03.2012 16:24:31
 */
@Named("themes")
@SessionScoped
public class ThemesBean implements Serializable {

	private static final long serialVersionUID = 5252485484727620L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ThemesBean.class);

	@Inject
	private transient ThemeRepository themeRepository;

	private Theme current;

	private Theme selected;

	private List<Theme> availableThemes = new ArrayList<>();

	private Map<String, Theme> themesByName = new LinkedHashMap<>();

	private List<SelectItem> supportedThemes = new ArrayList<>();

	/**
	 * Initializes the currently used theme according to the current user's
	 * preferences.
	 * 
	 * @throws IllegalStateException
	 *             if the configured theme is unknown.
	 */
	@PostConstruct
	public void onPostConstruct() {
		this.availableThemes = this.themeRepository.getAvailableThemes();
		LOGGER.info("Found [{}] available themes", availableThemes.size());
		availableThemes.forEach(t -> this.supportedThemes.add(new SelectItem(t.getName(), t.getLibraryName())));
		availableThemes.forEach(t -> this.themesByName.put(t.getName(), t));
		String defaultTheme = FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap()
				.get("primefaces.THEME");
		if (defaultTheme != null && !defaultTheme.startsWith("#{")) {
			this.current = this.themesByName.get(defaultTheme);
		}
		if (this.current == null) {
			this.current = this.availableThemes.get(0);
		}
		LOGGER.info("Using theme [{}] as current theme", this.current);
	}

	/**
	 * Returns the currently used theme.
	 */
	public Theme getCurrent() {
		return this.current;
	}

	public void setSelected(String themeName) {
		this.selected = this.themesByName.get(themeName);
	}

	public String getSelected() {
		return this.selected != null ? this.selected.getName() : this.current.getName();
	}

	public List<Theme> getAvailableThemes() {
		return this.availableThemes;
	}

	public List<SelectItem> getSupportedThemes() {
		return this.supportedThemes;
	}

	public String switchTheme() {
		LOGGER.info("Trying to switch to theme [{}]", this.selected);
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		if (this.selected == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one theme!", null));
			return null;
		}
		this.current = this.selected;
		this.selected = null;
		return facesContext.getViewRoot().getViewId() + "?faces-redirect=true";
	}
}
