/*
 * jeetrain-lightweight:UserPreferencesBean.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import edu.hm.cs.fwp.jeetrain.business.users.entity.Preference;
import edu.hm.cs.fwp.jeetrain.business.users.entity.PreferenceNames;
import edu.hm.cs.fwp.jeetrain.common.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.common.core.persistence.repository.GenericRepositoryBean;
import edu.hm.cs.fwp.jeetrain.common.core.persistence.repository.NamedQueryParameters;

/**
 * TODO: add documentation!
 * 
 * @author theism
 * @version 1.0
 * @since 05.01.2018
 */
@Stateless
@RolesAllowed("JEETRAIN_USER")
@Interceptors({ TraceInterceptor.class })
public class UserPreferencesBean {

	@Resource
	private SessionContext sessionContext;

	@Inject
	private GenericRepositoryBean genericRepository;

	public String getPreferenceValue(PreferenceNames preferenceName) {
		Preference entity = this.genericRepository.queryEntity(Preference.class, Preference.QUERY_BY_NAME,
				new NamedQueryParameters.Builder()
						.withParameter("userId", this.sessionContext.getCallerPrincipal().getName())
						.withParameter("preferenceName", preferenceName.name()).build());
		return entity != null ? entity.getValue() : null;
	}

	public void setPreferenceValue(PreferenceNames preferenceName, String preferenceValue) {
		Preference entity = this.genericRepository.queryEntity(Preference.class, Preference.QUERY_BY_NAME,
				new NamedQueryParameters.Builder()
						.withParameter("userId", this.sessionContext.getCallerPrincipal().getName())
						.withParameter("preferenceName", preferenceName.name()).build());
		if (entity != null) {
			entity.setValue(preferenceValue);
			this.genericRepository.setEntity(entity);
		} else {
			entity = new Preference(this.sessionContext.getCallerPrincipal().getName(), preferenceName.name());
			entity.setValue(preferenceValue);
			this.genericRepository.addEntity(entity);
		}
	}
}
