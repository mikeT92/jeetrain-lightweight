/*
 * jeetrain-lightweight:UserLocaleAwareViewHandler.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.i18n;

import java.util.Locale;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * {@code ViewHandler} Decorator that uses the locale of the currently
 * authenticated user if present.
 * 
 * @author theism
 * @version 1.0
 * @since 05.01.2018
 */
public class UserLocaleAwareViewHandler extends ViewHandlerWrapper {

	@Inject
	private I18nBean i18n;

	public UserLocaleAwareViewHandler(ViewHandler decorated) {
		super(decorated);
	}

	/**
	 * @see javax.faces.application.ViewHandlerWrapper#calculateLocale(javax.faces.context.FacesContext)
	 */
	@Override
	public Locale calculateLocale(FacesContext context) {
		Locale result = this.i18n.calculateUserLocale(context);
		if (result == null) {
			result = getWrapped().calculateLocale(context);
		}
		return result;
	}
}
