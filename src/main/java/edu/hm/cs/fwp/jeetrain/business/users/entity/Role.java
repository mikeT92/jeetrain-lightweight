/* Role.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Role that can be assigned to a JEETRAIN user.
 *  
 * @author Michael Theis
 */
@Entity
@Table(name = "T_ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 8985105701544649718L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RoleIdGenerator")
	@SequenceGenerator(name="RoleIdGenerator", sequenceName="USERS_SEQUENCE")
	@Column(name = "ROLE_ID")
	private long roleId;
	
	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	private Roles roleName;

	public Role() {
	}

	public Role(Roles roleName) {
		this.roleName = roleName;
	}
	
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleName(Roles roleName) {
		this.roleName = roleName;
	}

	public Roles getRoleName() {
		return roleName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (roleName != other.roleName)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.roleName.toString();
	}
}