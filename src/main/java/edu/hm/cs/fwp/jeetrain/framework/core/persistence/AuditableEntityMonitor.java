/*
 * jeetrain-lightweight:AuditableEntityMonitor.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.security.Principal;
import java.util.Calendar;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * {@code JPA lifecycle listener} that tracks creation and modification of
 * {@link AuditableEntity}s.
 * 
 * @author theism
 * @version 1.0
 * @since 05.01.2018
 */
public class AuditableEntityMonitor {

	@Inject
	private Principal principal;

	@PrePersist
	public void onPrePersist(AuditableEntity entity) {
		entity.trackCreation(this.principal.getName(), Calendar.getInstance().getTime());
	}

	@PreUpdate
	public void onPreUpdate(AuditableEntity entity) {
		entity.trackModification(this.principal.getName(), Calendar.getInstance().getTime());
	}
}
