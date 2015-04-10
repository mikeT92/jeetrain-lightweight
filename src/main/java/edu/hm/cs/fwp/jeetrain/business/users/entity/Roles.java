/* Roles.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.entity;

/**
 * Supported roles that can be assigned to users.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 16:05:17
 */
public enum Roles {

	/**
	 * Registered JEETrain user that can access the protected part of the application.
	 */
	JEETRAIN_USER,

	/**
	 * JEETrain administrator can manage users and tasks.
	 */
	JEETRAIN_ADMIN,
}
