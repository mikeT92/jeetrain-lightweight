/*
 * jeetrain-lightweight:AbstractAuditableEntity.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * Common base class for all {@code Entities} that are supposed to be auditable.
 * 
 * @author theism
 * @version 1.0
 * @since 09.01.2018
 */
@MappedSuperclass
@EntityListeners({ AuditableEntityMonitor.class })
public class AbstractAuditableEntity implements AuditableEntity {

	@Column(name = "CREATOR_USER_ID")
	@Size(max = 16)
	private String creatorId;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LAST_MODIFIER_USER_ID")
	@Size(max = 16)
	private String lastModifierId;

	@Column(name = "LAST_MODIFICATION_DATE")
	private Date lastModificationDate;

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getCreatorId()
	 */
	@Override
	public String getCreatorId() {
		return this.creatorId;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getCreationDate()
	 */
	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#trackCreation(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public void trackCreation(String creatorId, Date creationDate) {
		if (this.creatorId != null || this.creationDate != null) {
			throw new IllegalStateException(String.format(
					"Creation of entity [%s] has already been tracked: creatorId : %s, creationDate : %s!", toString(),
					this.creatorId, this.creationDate));
		}
		this.creatorId = creatorId;
		this.creationDate = creationDate;
		this.lastModifierId = creatorId;
		this.lastModificationDate = creationDate;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getLastModifierId()
	 */
	@Override
	public String getLastModifierId() {
		return this.lastModifierId;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getLastModificationDate()
	 */
	@Override
	public Date getLastModificationDate() {
		return this.lastModificationDate;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#trackModification(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public void trackModification(String lastModifierId, Date lastModificationDate) {
		this.lastModifierId = lastModifierId;
		this.lastModificationDate = lastModificationDate;
	}
}
