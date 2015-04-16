/* UserRegistrationBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * Session bean based implementation of a user registration service.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 16:21:50
 */
@Stateless
@Local(UserRegistration.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// @Interceptors({ TraceInterceptor.class, MethodValidationInterceptor.class })
public class UserRegistrationBean implements UserRegistration {

	// @Inject
	// private UserRepository userRepository;

	@Inject
	PasswordEncoderBean passwordEncoder;

	@Inject
	GenericRepositoryBean repository;

	@Override
	public User registerUser(User newUser) {
		if (newUser.getRoles().isEmpty()) {
			throw new IllegalArgumentException(
					"At least one role must be attached to the specified user!");
		}
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		this.repository.addEntity(newUser);
		return newUser;
	}

	@Override
	public boolean isUserNameAvailable(String userName) {
		return this.repository.getEntityById(User.class, userName) == null;
	}

	@Override
	public User retrieveUserById(String userId) {
		return this.repository.getEntityById(User.class, userId);
	}

	@Override
	public List<User> retrieveAllUsers() {
		return this.repository.queryEntities(User.class, User.QUERY_ALL, null);
	}

	@Override
	public List<User> retrieveAllUsers(int startIndex, int pageSize) {
		return this.repository.queryEntitiesWithPagination(User.class,
				User.QUERY_ALL, null, startIndex, pageSize);
	}

	@Override
	public void unregisterUser(String userId) {
		this.repository.removeEntityById(User.class, userId);
	}
}
