package com.billybang.loanservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
	INVALID_VALUE_TYPE(400, "C002", "Invalid Value Type"),
	ENTITY_NOT_FOUND(400, "C003", "Entity Not Found"),
	ACCESS_DENIED(403, "C004", "Access Denied"),
	METHOD_NOT_ALLOWED(405, "C005", "Method Not Allowed"),
	INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),

	// Business
	BUSINESS_ERROR(400, "B000", "Business Error");

	private final int status;
	private final String code;
	private final String message;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

}