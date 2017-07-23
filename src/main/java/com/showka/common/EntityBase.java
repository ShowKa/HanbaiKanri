package com.showka.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity Base.
 *
 */
@MappedSuperclass
@Getter
@Setter
public abstract class EntityBase {

	/**
	 * version
	 */
	@Version
	@Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 1")
	private Integer version;

	/**
	 * id
	 */
	@Column(name = "record_id", unique = true, nullable = false, length = 255)
	private String recordId;

	/**
	 * create_user_id
	 */
	@Column(
			name = "create_user_id",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "VARCHAR DEFAULT 'default'")
	private String create_user_id;

	/**
	 * create_function
	 */
	@Column(
			name = "create_function",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "VARCHAR DEFAULT 'default'")
	private String create_function;

	/**
	 * create_timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "create_timestamp",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date create_timestamp;

	/**
	 * update_user_id
	 */
	@Column(
			name = "update_user_id",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "VARCHAR DEFAULT 'default'")
	private String update_user_id;

	/**
	 * update_function
	 */
	@Column(
			name = "update_function",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "VARCHAR DEFAULT 'default'")
	private String update_function;

	/**
	 * update_timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "update_timestamp",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date update_timestamp;

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
