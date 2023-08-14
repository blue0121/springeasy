package io.jutil.springeasy.spring.exception;

import io.jutil.springeasy.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-01-02-16:22
 */
@Slf4j
@RestControllerAdvice
public class ErrorCodeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ErrorCodeException.class)
	public ResponseEntity<Object> handleErrorCodeException(ErrorCodeException ex,
	                                                       WebRequest request) {
		HttpStatus status = HttpStatus.resolve(ex.getHttpStatus());
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		String error = ex.toJsonString();
		HttpHeaders headers = new HttpHeaders();
		log.error("ErrorCodeException: {}", ex.getMessage());
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		String error = ErrorCode.ERROR.toJsonString();
		log.error("Exception: {}", ex.getMessage(), ex);
		return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		String error = this.getBindErrorResponse(ex);
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		log.error("Error, ", ex);
		String error = ErrorCode.INVALID_JSON.toJsonString();
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private String getBindErrorResponse(BindingResult bindingResult) {
		List<FieldError> errors = bindingResult.getFieldErrors();
		String errorStr = StringUtil.join(errors.stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.toList(),
				",");
		log.warn("BindException: {}", errorStr);
		return ErrorCode.INVALID_PARAM.toJsonString(errorStr);
	}
}
