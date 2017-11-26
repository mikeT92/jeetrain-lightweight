/*
 * jeetrain-lightweight:AuthenticatedUserComponentTestFixture.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;

import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * Test Fixture for component tests requiring an authenticated user.
 * 
 * @author theism
 * @version 1.0
 * @since 06.06.2017
 */
@Named
@Dependent
public class AuthenticatedUserComponentTestFixture {

	public static WebArchive attachToDeployment(WebArchive type) {
		type.addClass(AuthenticatedUserComponentTestFixture.class).addPackage(User.class.getPackage())
				.addClass(UserRegistrationBean.class).addClass(TraceInterceptor.class)
				.addClass(PasswordEncoderBean.class).addPackage(GenericRepositoryBean.class.getPackage())
				.addClass(UserBuilder.class).addAsResource("arquillian-persistence.xml", "META-INF/persistence.xml")
				.addAsResource(EmptyAsset.INSTANCE, "META-INF/beans.xml")
				.addAsWebInfResource("arquillian-web.xml", "web.xml")
				.addAsWebInfResource("arquillian-glassfish-web.xml", "glassfish-web.xml");
		return type;
	}

	@Inject
	private UserRegistrationBean userRegistration;

	private User testUser;

	private String testUserPassword;

	private ProgrammaticLogin login;

	public void onBefore() {
		ensureUser();
		loginUser();
	}

	public void onAfter() {
		logoutUser();
		disposeUser();
	}

	private void ensureUser() {
		if (this.testUser == null) {
			User newUser = new UserBuilder().build();
			String unhashedPassword = newUser.getPassword();
			this.userRegistration.registerUser(newUser);
			this.testUser = newUser;
			this.testUserPassword = unhashedPassword;
		}
	}

	private void loginUser() {
		ProgrammaticLogin myLogin = new ProgrammaticLogin();
		try {
			myLogin.login(this.testUser.getUserName(), this.testUserPassword.toCharArray(), "JEETRAIN_REALM", true);
			this.login = myLogin;
		} catch (Exception ex) {
			throw new IllegalStateException(
					String.format("Unable to login test user [%s]", this.testUser.getUserName()), ex);
		}
	}

	private void logoutUser() {
		if (this.login != null) {
			this.login.logout();
			this.login = null;
		}
	}

	private void disposeUser() {
		if (this.testUser != null) {
			try {
				this.userRegistration.unregisterUser(testUser.getUserName());
			} catch (Exception ex) {
				System.err.println(
						String.format("Unable to dispose of test user [%s] due to exception [%s]; continue anyway",
								this.testUser.getUserName(), ex.getMessage()));
			}
			this.testUser = null;
			this.testUserPassword = null;
		}
	}
}
