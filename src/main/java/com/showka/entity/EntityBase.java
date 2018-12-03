package com.showka.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OptimisticLockException;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity Base.
 *
 */
@MappedSuperclass
@Getter
public abstract class EntityBase {

	@Transient
	@Getter(value = AccessLevel.PACKAGE)
	private boolean isNew;

	@PrePersist
	public void setWhetherNew() {
		this.isNew = true;
	}

	/**
	 * version
	 */
	@Version
	@Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 1")
	private Integer version;

	/**
	 * id
	 */
	@Column(name = "record_id", unique = true, nullable = false, length = 255, insertable = true, updatable = false)
	@Setter
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
	@Setter(AccessLevel.PROTECTED)
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
	@Setter(AccessLevel.PROTECTED)
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
	@Setter(AccessLevel.PROTECTED)
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
	@Setter(AccessLevel.PROTECTED)
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
	@Setter(AccessLevel.PROTECTED)
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
	@Setter(AccessLevel.PROTECTED)
	private Date update_timestamp;

	/**
	 * 
	 * @param version
	 */
	public void setVersion(Integer version) {
		if (this.version == null && version != null) {
			throw new OptimisticLockException(this.getClass().getSimpleName()
					+ " : OptimisticLockException!! version should be null but was <" + version + ">");
		}
		if (this.version != null && !this.version.equals(version)) {
			throw new OptimisticLockException(
					this.getClass().getSimpleName() + " : OptimisticLockException!! expected version is <"
							+ this.version + "> but was <" + version + ">");
		}
		this.version = version;
	}

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
