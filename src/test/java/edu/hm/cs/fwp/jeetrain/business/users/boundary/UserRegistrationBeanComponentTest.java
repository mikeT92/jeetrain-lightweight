package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

@RunWith(Arquillian.class)
public class UserRegistrationBeanComponentTest {

	/**
	 * Baut ein EJB-JAR mit den zu testenden Klassen.
	 * 
	 * @return EJB-Modul
	 */
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive ejbModule = ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(User.class.getPackage())
				.addClass(UserRegistration.class)
				.addClass(UserRegistrationBean.class)
				.addClass(PasswordEncoderBean.class)
				.addPackage(GenericRepositoryBean.class.getPackage())
				.addClass(UserBuilder.class)
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
		return ejbModule;
	}

	@Inject
	private UserRegistration underTest;
	
	private final List<User> trashBin = new ArrayList<>();
	
	@After
	public void onAfter() {
		for (User current : trashBin) {
			try {
				this.underTest.unregisterUser(current.getUserName());
			} catch (Exception ex) {
				System.err.println();
			}
		}
		trashBin.clear();
	}
	
	@Test
	public void testAddUserStoresAllFields() {
		User newUser = new UserBuilder().build();
		this.underTest.registerUser(newUser);
		this.trashBin.add(newUser);
		User persistentUser = this.underTest.retrieveUserById(newUser.getUserName());
		assertNotNull("Hinzugefügter User muss gefunden werden", persistentUser);
	}

}
