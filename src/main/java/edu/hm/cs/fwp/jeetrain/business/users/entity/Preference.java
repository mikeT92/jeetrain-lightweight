/*
 * jeetrain-lightweight:Preference.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.business.users.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * {@code Entity} representing a single user preference.
 * 
 * @author theism
 * @version 1.0
 * @since 05.01.2018
 */
@Entity
@Table(name = "T_PREFERENCE")
@NamedQueries({
		@NamedQuery(name = Preference.QUERY_BY_NAME, query = "SELECT p FROM Preference p WHERE p.userId = :userId AND p.name = :preferenceName") })
public class Preference implements Serializable {

	public static final String QUERY_BY_NAME = "edu.hm.cs.fwp.jeetrain.business.users.entity.Preference.QUERY_BY_NAME";

	private static final long serialVersionUID = 3817046600545092760L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PREFERENCE_ID")
	private long id;

	@Column(name = "USER_ID")
	@NotNull
	@Size(max = 16)
	private String userId;

	@Column(name = "NAME")
	@NotNull
	@Size(max = 32)
	private String name;

	@Column(name = "VALUE")
	@Size(max = 256)
	private String value;

	@Column(name = "VERSION")
	@Version
	private long version;

	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationDate;

	public Preference() {

	}

	public Preference(String userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	/**
	 * Unique surrogate ID of this preference.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Unique ID of the user that owns this preference.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Name of this preference.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Value of this preference.
	 */
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Preference other = (Preference) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getClass().getSimpleName()).append(" {");
		result.append(" id : ").append(this.id);
		result.append(", userId : \"").append(this.userId).append("\"");
		result.append(", name : \"").append(this.name).append("\"");
		if (this.value != null) {
			result.append(", value : \"").append(this.name).append("\"");
		}
		result.append(" }");
		return result.toString();
	}
}
