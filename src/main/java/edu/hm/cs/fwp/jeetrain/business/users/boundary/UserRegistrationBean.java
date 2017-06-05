/* UserRegistrationBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * Session bean based implementation of a user registration service.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 16:21:50
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ TraceInterceptor.class })
public class UserRegistrationBean {

	// @Inject
	// private UserRepository userRepository;

	@Inject
	PasswordEncoderBean passwordEncoder;

	@Inject
	GenericRepositoryBean repository;

	public User registerUser(User newUser) {
		if (!isUserNameAvailable(newUser.getUserName())) {
			throw new IllegalArgumentException(
					String.format("Username [%s] is already taken! Please select another one.", newUser.getUserName()));
		}
		if (newUser.getRoles().isEmpty()) {
			throw new IllegalArgumentException("At least one role must be attached to the specified user!");
		}
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		this.repository.addEntity(newUser);
		return newUser;
	}

	public boolean isUserNameAvailable(String userName) {
		return this.repository.getEntityById(User.class, userName) == null;
	}

	public User retrieveUserById(String userId) {
		return this.repository.getEntityById(User.class, userId);
	}

	public List<User> retrieveAllUsers() {
		return this.repository.queryEntities(User.class, User.QUERY_ALL, null);
	}

	public List<User> retrieveAllUsers(int startIndex, int pageSize) {
		return this.repository.queryEntitiesWithPagination(User.class, User.QUERY_ALL, null, startIndex, pageSize);
	}

	public void unregisterUser(String userId) {
		this.repository.removeEntityById(User.class, userId);
	}
}
