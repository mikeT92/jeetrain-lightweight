/*
 * jeetrain-lightweight:PasswordEncoderBeanTest.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.business.users.control;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO: add documentation!
 * 
 * @author theism
 * @version 1.0
 * @since 29.12.2017
 */
public class PasswordEncoderBeanTest {

	private PasswordEncoderBean underTest = new PasswordEncoderBean();

	@Test
	public void encodeReturnsNonEmptyEncodedPassword() {
		String encodedPassword = this.underTest.encode("arquillian");
		assertNotNull("encode must return non-null encoded password", encodedPassword);
		assertFalse("encode must return non-empty encoded password", encodedPassword.isEmpty());
		System.out.println(encodedPassword);
	}
}
