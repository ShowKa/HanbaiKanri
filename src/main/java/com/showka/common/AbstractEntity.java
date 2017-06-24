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
public abstract class AbstractEntity {

	/**
	 * version
	 */
	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	/**
	 * id
	 */
	@Column(name = "id", unique = true, nullable = false, length = 255)
	private String id;

	/**
	 * create_user_id
	 */
	@Column(name = "create_user_id", unique = false, nullable = false, length = 255)
	private String create_user_id;

	/**
	 * create_user_name
	 */
	@Column(name = "create_user_name", unique = false, nullable = false, length = 255)
	private String create_user_name;

	/**
	 * create_timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_timestamp", unique = false, nullable = false, length = 255)
	private Date create_timestamp;

	/**
	 * update_user_id
	 */
	@Column(name = "update_user_id", unique = false, nullable = false, length = 255)
	private String update_user_id;

	/**
	 * update_user_name
	 */
	@Column(name = "update_user_name", unique = false, nullable = false, length = 255)
	private String update_user_name;

	/**
	 * update_timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_timestamp", unique = false, nullable = false, length = 255)
	private Date update_timestamp;

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
