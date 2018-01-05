/* I18nBean.java 
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.i18n;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Managed bean that controls all user preferences regarding locales and
 * regional settings.
 * <p>
 * The following preferences regarding internationalization are supported:
 * </p>
 * <ul>
 * <li><b>locale</b></li>
 * <li><b>timeZone</b></li>
 * <li><b>datePattern</b></li>
 * <li><b>dateTimePattern</b></li>
 * <li><b>timePattern</b></li>
 * </ul>
 * 
 * @author mtheis
 * @version 1.0
 * @since release 1.0
 */
@Named("i18n")
@SessionScoped
public class I18nBean implements Serializable {

	private static final long serialVersionUID = -343816564756901520L;

	private static final Logger logger = Logger.getLogger(I18nBean.class.getName());

	private List<SelectItem> supportedLocales = new ArrayList<SelectItem>();

	private Locale locale;

	private TimeZone timeZone;

	private String datePattern;

	private String dateTimePattern;

	private String timePattern;

	private Locale selectedLocale;

	private Locale currentLocale;

	private List<Locale> availableLocales = new ArrayList<>();

	/**
	 * Check the required preconditions and initializes this managed bean with some
	 * default values.
	 */
	@PostConstruct
	public void onPostConstruct() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale defaultLocale = facesContext.getApplication().getDefaultLocale();
		if (defaultLocale != null) {
			this.availableLocales.add(defaultLocale);
			this.supportedLocales.add(new SelectItem(defaultLocale.toString(), buildLocaleLabel(defaultLocale)));
		}
		Iterator<Locale> supportedLocales = facesContext.getApplication().getSupportedLocales();
		while (supportedLocales.hasNext()) {
			Locale current = supportedLocales.next();
			this.availableLocales.add(current);
			this.supportedLocales.add(new SelectItem(current.toString(), buildLocaleLabel(current)));
		}

		this.locale = calculateUserLocale(facesContext);
		this.currentLocale = this.locale;

		calculateValues(facesContext);
		logger.info(String.format("available locales: [%s], current locale: [%s]",
				StringUtils.join(this.availableLocales, ", "), this.currentLocale));
	}

	/**
	 * Returns the locale currently active for this HTTP session.
	 */
	public Locale getLocale() {
		return this.locale;
	}

	public List<SelectItem> getSupportedLocales() {
		return this.supportedLocales;
	}

	public String getSelectedLocale() {
		return this.selectedLocale != null ? this.selectedLocale.toString() : this.locale.toString();
	}

	public void setSelectedLocale(String localeText) {
		logger.info("setting selected locale [" + localeText + "]");
		this.selectedLocale = LocaleUtils.toLocale(localeText);
	}

	public String switchLocale() {
		logger.info("Switching locale to [" + this.selectedLocale + "]");
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		if (this.selectedLocale == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a language!", null));
			return null;
		}
		this.locale = this.selectedLocale;
		this.currentLocale = this.locale;
		this.selectedLocale = null;
		facesContext.getViewRoot().setLocale(this.currentLocale);
		calculateValues(facesContext);

		return facesContext.getViewRoot().getViewId() + "?faces-redirect=true";
	}

	public void onLocaleChanged() {
		logger.info("User changed locale to [" + this.selectedLocale + "]");
		switchLocale();
	}

	/**
	 * Calculates the locale of the currently authenticated user.
	 */
	public Locale calculateUserLocale(FacesContext facesContext) {
		Locale result = this.currentLocale;
		if (result == null) {
			result = getFallbackLocale(facesContext);
		}
		return result;
	}

	/**
	 * Returns the timezone currently active for this HTTP session.
	 */
	public TimeZone getTimeZone() {
		return this.timeZone;
	}

	/**
	 * Returns the format pattern for date values currently active for this HTTP
	 * session.
	 */
	public String getDatePattern() {
		return this.datePattern;
	}

	/**
	 * Returns the format pattern for datetime values currently active for this HTTP
	 * session.
	 */
	public String getDateTimePattern() {
		return this.dateTimePattern;
	}

	/**
	 * Returns the format pattern for time values currently active for this HTTP
	 * session.
	 */
	public String getTimePattern() {
		return this.timePattern;
	}

	/**
	 * Calculates the currently active locale.
	 */
	private Locale calculateLocale(FacesContext facesContext) {
		Locale result = facesContext.getViewRoot().getLocale();
		if (!isSupportedLocale(result)) {
			Locale fallbackLocale = getFallbackLocale(facesContext);
			logger.warning("Falling back to locale [" + fallbackLocale + "] since the calculated locale [" + result
					+ "] is not supported by the current application. Did you forget to add a local-config element to your faces-config.xml?");
			if (ProjectStage.Development == facesContext.getApplication().getProjectStage()) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, MessageFormat.format(
						"Falling back to locale [{0}] since the calculated locale [{1}] is not supported by the current application. Did you forget to add a local-config element to your faces-config.xml?",
						fallbackLocale, result), null);
				facesContext.addMessage(null, message);
			}
			result = fallbackLocale;
		}
		return result;
	}

	/**
	 * Returns <code>true</code> if the specified locale is among the supported
	 * locales; otherwise <code>false</code>.
	 */
	private boolean isSupportedLocale(Locale locale) {
		return this.availableLocales.contains(locale);
	}

	/**
	 * Returns the fallback locale for the current application.
	 */
	private Locale getFallbackLocale(FacesContext facesContext) {
		Locale result = facesContext.getApplication().getDefaultLocale();
		if (result == null) {
			result = Locale.getDefault();
		}
		return result;
	}

	/**
	 * Calculates the currently active timezone.
	 */
	private TimeZone calculateTimeZone(FacesContext facesContext) {
		TimeZone result = TimeZone.getDefault();
		return result;
	}

	/**
	 * Calculates the currently active format pattern used for date parts of
	 * java.util.Date values.
	 */
	private String calculateDatePattern(FacesContext facesContext) {
		String result = "dd.MM.yyyy";
		return result;
	}

	/**
	 * Calculates the currently active format pattern used for date and time parts
	 * of java.util.Date values.
	 */
	private String calculateDateTimePattern(FacesContext facesContext) {
		String result = "dd.MM.yyyy HH:mm:ss";
		return result;
	}

	/**
	 * Calculates the currently active format pattern used for time parts of
	 * java.util.Date values.
	 */
	private String calculateTimePattern(FacesContext facesContext) {
		String result = "HH:mm:ss";
		return result;
	}

	/**
	 * Calculates the current values for I18N properties.
	 */
	private void calculateValues(FacesContext facesContext) {
		this.datePattern = calculateDatePattern(facesContext);
		this.dateTimePattern = calculateDateTimePattern(facesContext);
		this.timePattern = calculateTimePattern(facesContext);
		this.timeZone = calculateTimeZone(facesContext);
	}

	private String buildLocaleLabel(Locale locale) {
		StringBuilder result = new StringBuilder();
		String language = locale.getLanguage();
		if (language != null) {
			result.append(language.toUpperCase());
		}
		String country = locale.getCountry();
		if (country != null) {
			result.append(" (").append(country).append(")");
		}
		return result.toString();
	}
}
