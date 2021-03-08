package com.jumia.phonenumbers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

	private String message;
	private HttpStatus status;

	public BusinessException(String message, HttpStatus status) {
		super(message);
		this.message = message;
		this.status = status;
	}

}
