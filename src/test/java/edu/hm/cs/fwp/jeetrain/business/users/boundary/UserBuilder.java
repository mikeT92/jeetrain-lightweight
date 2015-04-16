package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import java.util.Calendar;
import java.util.Date;

import edu.hm.cs.fwp.jeetrain.business.users.entity.Gender;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Role;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Roles;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

public class UserBuilder {

	private Date dateOfBirth = Calendar.getInstance().getTime();
	private String password = "jeetrain2015";
	private String email = "john.doe@hm.edu";
	private String firstName = "John";
	private String fullName = "John Doe";
	private Gender gender = Gender.MALE;
	private String lastName = "Doe";
	private String userName = "johndoe";
	
	public User build() {
		User result = new User();
		result.setDateOfBirth(this.dateOfBirth);
		result.setEmail(email);
		result.setFirstName(firstName);
		result.setFullName(fullName);
		result.setGender(gender);
		result.setLastName(lastName);
		result.setUserName(userName);
		result.setPassword(this.password);
		result.setConfirmedPassword(this.password);
		result.getRoles().add(new Role(Roles.JEETRAIN_USER));
		return result;
	}
}
