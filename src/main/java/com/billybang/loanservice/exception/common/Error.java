package com.billybang.loanservice.exception.common;

public interface Error {
	String getCode();

	String getMessage(String... values);
}
