/*
 * jeetrain-lightweight:CurrentUserEditor.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.presentation.users;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.users.boundary.UserRegistrationBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

/**
 * Editor that manages the current user section within the page header.
 * 
 * @author theism
 * @version 1.0
 * @since 05.06.2017
 */
@Named("currentUserEditor")
@SessionScoped
public class CurrentUserEditor implements Serializable {

	private static final long serialVersionUID = -6602369115639082145L;

	private User currentUser;

	@Inject
	private UserRegistrationBean boundary;

	public boolean hasCurrentUser() {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("JEETRAIN_USER");
	}

	public String getCurrentUserId() {
		return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}

	public User getCurrentUser() {
		User result = null;
		if (hasCurrentUser()) {
			if (this.currentUser == null) {
				this.currentUser = this.boundary.retrieveUserById(getCurrentUserId());
			}
			result = this.currentUser;
		}
		return result;
	}
}
