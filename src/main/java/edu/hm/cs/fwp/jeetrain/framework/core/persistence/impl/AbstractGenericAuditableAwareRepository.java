/* AbstractGenericAuditableAwareRepository.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence.impl;

import java.util.Calendar;
import java.util.Date;

import edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity;

/**
 * Abstract {@code GenericRepository} implementation that supports
 * {@code AuditableEntity} types by tracking their creation and modification.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 03.07.2012 10:46:28
 */
public abstract class AbstractGenericAuditableAwareRepository<K, T extends AuditableEntity> extends
		AbstractGenericRepository<K, T> {

	/**
	 * Tracks information about the entity's creation before persisting it.
	 * @see eu.unicredit.xframe.system.core.persistence.impl.AbstractGenericRepository#addEntity(java.lang.Object)
	 */
	@Override
	public T addEntity(T entity) {
		trackCreation(entity);
		return super.addEntity(entity);
	}

	/**
	 * Tracks information about the entity's modification before merging it.
	 * @see eu.unicredit.xframe.system.core.persistence.impl.AbstractGenericRepository#setEntity(java.lang.Object)
	 */
	@Override
	public T setEntity(T entity) {
		trackModification(entity);
		return super.setEntity(entity);
	}
	
	/** 
	 * Returns the principal name of the currently authenticated user.
	 * <p>
	 * In most cases, stateless session bean based subclasses will retrieve this information by calling 
	 * {@code javax.ejb.SessionContext#getCallerPrincipal().getName()}
	 * </p>
	 */
	protected abstract String getCallerId();
	
	private void trackCreation(AuditableEntity entity) {
		Date creationDate = Calendar.getInstance().getTime();
		String creatorId = getCallerId();
		entity.trackCreation(creatorId, creationDate);
		entity.trackModification(creatorId, creationDate);
	}
	
	private void trackModification(AuditableEntity entity) {
		Date lastModificationDate = Calendar.getInstance().getTime();
		String lastModifierId = getCallerId();
		entity.trackModification(lastModifierId, lastModificationDate);
	}
}
