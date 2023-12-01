package com.assessment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageStatus {

	// @formatter:off

    SENT("sent"),
    RECEIVED("received");
    
    // @formatter:on

	private final String value;

	private MessageStatus(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static MessageStatus of(String value) {
		for (MessageStatus type : MessageStatus.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}

		return null;
	}
}
