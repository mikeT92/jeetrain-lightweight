/* AuditableEntity.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.util.Date;

/**
 * Marks an entity as auditable by retaining information about modification to
 * an entity.
 * <p>
 * The following information will be retained:
 * </p>
 * <ul>
 * <li><b>creatorId</b>: user ID of the user that created this entity</li>
 * <li><b>creationDate</b>: date/time this entity was created</li>
 * <li><b>lastModifierId</b>: user ID of the user that modified this entity the
 * last time</li>
 * <li><b>lastModification</b>: date/time this entity was modified the last time
 * </li>
 * </ul>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 03.07.2012 10:47:54
 */
public interface AuditableEntity {

	/**
	 * Returns the user ID of the user that created this entity.
	 */
	public String getCreatorId();

	/**
	 * Returns the creation date and time of this entity.
	 */
	public Date getCreationDate();

	/**
	 * Tells this entity to retain the specified user ID and point in time as
	 * the creatorId and the creationDate of this entity.
	 * <p>
	 * This method can only be called once during an entities life to make sure
	 * that information about an entity's creation can not be changed after the
	 * entity has been successully persisted for the first time.
	 * </p>
	 * <p>
	 * Usually this method is called by {@code Repository} implementations that
	 * support auditable entities.
	 * </p>
	 */
	public void trackCreation(String creatorId, Date creationDate);

	/**
	 * Returns the user ID of the user that modified this entity the last time.
	 */
	public String getLastModifierId();

	/**
	 * Returns the date and time of the last modification of this entity.
	 */
	public Date getLastModificationDate();

	/**
	 * Tells this entity to retain the specified user ID and point in time as
	 * the lastModifierId and the lastModificationDate of this entity.
	 * <p>
	 * Usually this method is called by {@code Repository} implementations that
	 * support auditable entities.
	 * </p>
	 */
	public void trackModification(String lastModifierId, Date lastModificationDate);
}
