/* UserRepositoryBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.integration.users;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.impl.AbstractGenericRepository;

/**
 * Stateless session bean based implementation of repository
 * {@code UserRepository}. All persistence logic is implemented by the generic
 * abstract base class {@code GenericRepositoryBean}.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 02.05.2012 17:59:38
 */
@Stateless
@Interceptors({ TraceInterceptor.class })
public class UserRepositoryBean extends AbstractGenericRepository<Long, User>
		implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

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
	protected Class<User> getEntityType() {
		return User.class;
	}
}
