/*
 * jeetrain-lightweight:LocalDateTimeConverter.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * {@code JPA Converter} that maps values of {@link LocalDateTime} entity
 * attributes to {@link Timestamp} database columns and viceversa.
 * 
 * @author mikeT92
 * @version 1.0
 * @since 23.01.2018
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	/**
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
		Timestamp result = null;
		if (attribute != null) {
			result = Timestamp.valueOf(attribute);
		}
		return result;
	}

	/**
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
		LocalDateTime result = null;
		if (dbData != null) {
			result = dbData.toLocalDateTime();
		}
		return result;
	}

}
