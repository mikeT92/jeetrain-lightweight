/*
 * jeetrain-lightweight:LocaleConverter.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.i18n;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Singleton;

import org.apache.commons.lang3.LocaleUtils;

/**
 * {@code Converter} for {@link Locale} instances.
 * 
 * @author theism
 * @version 1.0
 * @since 05.01.2018
 */
@Singleton
@FacesConverter(value = "localeConverter", managed = true)
public class LocaleConverter implements Converter<Locale> {

	/**
	 * Converts the given {@code String} value into a {@code Locale} instance.
	 * 
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Locale getAsObject(FacesContext context, UIComponent component, String value) {
		Locale result = null;
		if (value != null && !value.isEmpty()) {
			result = LocaleUtils.toLocale(value);
		}
		return result;
	}

	/**
	 * Converts the given {@code Locale} instance into a {@code String} value.
	 * 
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Locale value) {
		String result = null;
		if (value != null) {
			result = value.toString();
		}
		return result;
	}

}
