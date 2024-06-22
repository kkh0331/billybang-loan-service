package com.billybang.loanservice.exception;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.api.ApiUtils;
import com.billybang.loanservice.exception.common.CommonException;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할 경우 발생
	 * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("handleMethodArgumentNotValidException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return ResponseEntity.badRequest().body(ApiUtils.error(response));
	}

	/**
	 * @ModelAttribute 으로 binding error 발생시 BindException 발생한다.
	 * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
	 */
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleBindException(BindException e) {
		log.error("handleBindException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
		return ResponseEntity.badRequest().body(ApiUtils.error(response));
	}

	/**
	 * @PathVariable 에서 validation을 할 때 binding error가 발생하는 경우
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleConstraintViolationException(ConstraintViolationException e) {
		log.error("handleConstraintViolationException {}", e.getMessage());
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, constraintViolations);
		return ResponseEntity.badRequest().body(ApiUtils.error(response));
	}

	/**
	 * 주로 @RequestParam에서 enum으로 binding 못할 경우 발생
	 * @PathVariable에서 string -> int/long 등의 숫자 타입 binding 못할 경우
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		log.error("handleMethodArgumentTypeMismatchException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.badRequest().body(ApiUtils.error(response));
	}

	/**
	 * body to dto 과정에서 enum 형태로 deserialize 하지 못하는 경우 발생
	 * 또한, 숫자가 int 혹은 long type 등의 범위를 벗어나는 경우 발생
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleHttpMessageNotReadableExceptionException(
			HttpMessageNotReadableException e) {
		log.error("handleHttpMessageNotReadableExceptionException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.badRequest().body(ApiUtils.error(response));
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiUtils.error(response));
	}

	/**
	 * 필요한 권한을 보유하지 않은 경우 발생
	 */
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleAccessDeniedException(AccessDeniedException e) {
		log.error("handleAccessDeniedException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.ACCESS_DENIED);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiUtils.error(response));
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ApiResult<ErrorResponse>> handleCommonException(final CommonException e) {
		log.error("CommonException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.status(response.getStatus()).body(ApiUtils.error(response));
	}

	@ExceptionHandler(FeignException.BadRequest.class)
	public ResponseEntity<ApiResult<ErrorResponse>> handleFeignBadRequestException(FeignException.BadRequest e) {
		log.error("FeignException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.badRequest().body(ApiUtils.error(response));
	}

	@ExceptionHandler(FeignException.Unauthorized.class)
	public ResponseEntity<ApiResult<ErrorResponse>> handleFeignUnauthorizedException(FeignException.Unauthorized e) {
		log.error("FeignException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiUtils.error(response));
	}

	@ExceptionHandler(FeignException.Forbidden.class)
	public ResponseEntity<ApiResult<ErrorResponse>> handleFeignForbiddenException(FeignException.Forbidden e) {
		log.error("FeignException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiUtils.error(response));
	}

	@ExceptionHandler(FeignException.InternalServerError.class)
	public ResponseEntity<ApiResult<ErrorResponse>> handleFeignInternalServerException(FeignException.InternalServerError e) {
		log.error("FeignException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiUtils.error(response));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResult<ErrorResponse>> handleUnExpectException(Exception e) {
		log.error("UnExpectException {}", e.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return ResponseEntity.internalServerError().body(ApiUtils.error(response));
	}
}