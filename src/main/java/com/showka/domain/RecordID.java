package com.showka.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RecordID {

	private String recordId;

	public RecordID() {
		this.recordId = UUID.randomUUID().toString().substring(0, 8);
	}

	@Override
	public String toString() {
		return this.recordId;
	}
}
