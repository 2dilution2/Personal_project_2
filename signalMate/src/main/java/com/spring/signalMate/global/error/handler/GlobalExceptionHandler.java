package com.spring.signalMate.global.error.handler;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

import com.spring.signalMate.global.error.model.ErrorResponse;
import com.spring.signalMate.global.error.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.status(ex.getErrorCode().getStatus().value())
			.code(ex.getErrorCode().getCode())
			.message(ex.getErrorCode().getMessage())
			.build();
		return new ResponseEntity<>(errorResponse, ex.getErrorCode().getStatus());
	}

	// 예외 처리: 유효하지 않은 요청 데이터(400 Bad Request)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.status(HttpStatus.BAD_REQUEST.value())
			.code("BAD_REQUEST")
			.message(ex.getMessage())
			.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// 예외 처리: 권한 없음(403 Forbidden)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.status(HttpStatus.FORBIDDEN.value())
			.code("FORBIDDEN")
			.message("Access is denied")
			.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}

	// 예외 처리: 리소스 없음(404 Not Found)
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.status(HttpStatus.NOT_FOUND.value())
			.code("NOT_FOUND")
			.message("Resource not found")
			.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// 예외 처리: 서버 내부 오류(500 Internal Server Error)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.code("INTERNAL_SERVER_ERROR")
			.message("An unexpected internal server error occurred")
			.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 기타 예외 핸들러들을 여기에 추가...

}
