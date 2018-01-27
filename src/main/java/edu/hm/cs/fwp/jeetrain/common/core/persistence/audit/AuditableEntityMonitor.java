/*
 * jeetrain-lightweight:AuditableEntityMonitor.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.common.core.persistence.audit;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

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
// @Named
// @Singleton
public class AuditableEntityMonitor {

	private static final Logger logger = Logger.getLogger(AuditableEntityMonitor.class.getName());

	@Inject
	private Principal principal;

	@PrePersist
	public void onPrePersist(AuditableEntity entity) {
		logger.info(String.format("Tracking creation of entity [%s] by user [%s]", entity, getAuthenticatedUserName()));
		entity.trackCreation(getAuthenticatedUserName(), LocalDateTime.now());
	}

	@PreUpdate
	public void onPreUpdate(AuditableEntity entity) {
		logger.info(
				String.format("Tracking modification of entity [%s] by user [%s]", entity, getAuthenticatedUserName()));
		entity.trackModification(getAuthenticatedUserName(), LocalDateTime.now());
	}

	private String getAuthenticatedUserName() {
		return this.principal != null ? this.principal.getName() : null;
	}
}
