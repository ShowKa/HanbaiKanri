package com.showka.domain;

public abstract class DomainMeisai extends DomainEntity implements Comparable<DomainMeisai> {

	@Override
	public final boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (other instanceof DomainMeisai) {
			return equals((DomainMeisai) other);
		}
		return false;
	}

	protected boolean equals(DomainMeisai other) {
		return this.getMeisaiNumber().equals(other.getMeisaiNumber());
	}

	@Override
	public int compareTo(DomainMeisai o) {
		return this.getMeisaiNumber().compareTo(o.getMeisaiNumber());
	}

	@Override
	public int hashCode() {
		return this.getMeisaiNumber().hashCode();
	}

	protected abstract Integer getMeisaiNumber();
}
