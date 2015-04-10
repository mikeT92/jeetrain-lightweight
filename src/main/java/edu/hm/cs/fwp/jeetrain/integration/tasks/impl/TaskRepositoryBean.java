/* TaskRepositoryBean.java 
 */
package edu.hm.cs.fwp.jeetrain.integration.tasks.impl;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.impl.AbstractGenericAuditableAwareRepository;
import edu.hm.cs.fwp.jeetrain.integration.tasks.TaskRepository;

/**
 * @author theism
 * 
 */
@Stateless
@Local(TaskRepository.class)
@RolesAllowed("JEETRAIN_USER")
@Interceptors({ TraceInterceptor.class })
public class TaskRepositoryBean extends
		AbstractGenericAuditableAwareRepository<Long, Task> implements
		TaskRepository {

	@Resource
	private SessionContext sessionContext;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.impl.AbstractGenericAuditableAwareRepository#getCallerId()
	 */
	@Override
	protected String getCallerId() {
		return this.sessionContext.getCallerPrincipal().getName();
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.impl.AbstractGenericRepository#getEntityManager()
	 */
	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.impl.AbstractGenericRepository#getEntityType()
	 */
	@Override
	protected Class<Task> getEntityType() {
		return Task.class;
	}

}
