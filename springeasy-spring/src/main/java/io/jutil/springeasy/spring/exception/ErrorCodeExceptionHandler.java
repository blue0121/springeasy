package io.jutil.springeasy.spring.exception;

import io.jutil.springeasy.core.security.TokenException;
import io.jutil.springeasy.core.util.StringUtil;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Jin Zheng
 * @since 2023-10-05
 */
@Slf4j
@RestControllerAdvice
public class ErrorCodeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ErrorCodeException.class)
	public ResponseEntity<Object> handleErrorCodeException(ErrorCodeException ex,
	                                                       WebRequest request) {
		var status = HttpStatus.resolve(ex.getHttpStatus());
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		var error = ex.toJsonString();
		var headers = new HttpHeaders();
		log.error("ErrorCodeException: {}", ex.getMessage());
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationException(ValidationException ex,
	                                                        WebRequest request) {
		var headers = new HttpHeaders();
		var message = ex.getMessage();
		if (ex instanceof ConstraintViolationException ex2) {
			message = ex2.getMessage();
		}

		var error = BaseErrorCode.INVALID_PARAM.toJsonString(message);
		log.error("ValidationException: {}", ex.getMessage());
		return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST,
				request);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Object> handleTokenException(TokenException ex,
	                                                   WebRequest request) {
		var headers = new HttpHeaders();
		var error = BaseErrorCode.INVALID_TOKEN.toJsonString(ex.getMessage());
		log.error("TokenException: {}", ex.getMessage());
		return handleExceptionInternal(ex, error, headers, HttpStatus.UNAUTHORIZED,
				request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
		var headers = new HttpHeaders();
		var error = BaseErrorCode.SYS_ERROR.toJsonString();
		log.error("Exception: {}", ex.getMessage(), ex);
		return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		var error = this.getBindErrorResponse(ex);
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		log.error("Error, ", ex);
		var error = BaseErrorCode.INVALID_JSON.toJsonString();
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private String getBindErrorResponse(BindingResult bindingResult) {
		var errors = bindingResult.getFieldErrors();
		var errorStr = StringUtil.join(errors.stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.toList(),
				",");
		log.warn("BindException: {}", errorStr);
		return BaseErrorCode.INVALID_PARAM.toJsonString(errorStr);
	}
}
