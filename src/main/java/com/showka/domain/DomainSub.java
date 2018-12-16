package com.showka.domain;

public abstract class DomainSub extends DomainEntity {

	/**
	 * 当値判定.
	 * 
	 * @param other
	 *            比較対象
	 * @return RecordIDが同じならtrue
	 */
	protected boolean equals(DomainSub other) {
		return this.getRecordId().equals(other.getRecordId());
	}

	@Override
	public final boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (other instanceof DomainSub) {
			return equals((DomainSub) other);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getRecordId().hashCode();
	}

}
