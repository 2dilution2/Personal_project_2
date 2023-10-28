package com.spring.signalMate.global.error.exception;

import com.spring.signalMate.global.error.type.ErrorCode;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
	private final ErrorCode errorCode;

	public ServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}