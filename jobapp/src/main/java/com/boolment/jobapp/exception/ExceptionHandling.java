package com.boolment.jobapp.exception;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.boolment.jobapp.entity.HttpResponse;
import com.boolment.jobapp.util.ConstantVariables;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<HttpResponse> accountDisabledExceptioin() {
		return createHttpResponse(HttpStatus.BAD_REQUEST, ConstantVariables.ACCOUNT_DISABLED);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<HttpResponse> badCredentialsException() {
		return createHttpResponse(HttpStatus.BAD_REQUEST, ConstantVariables.INCORRECT_CREDENTIALS);
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<HttpResponse> accessDeniedExceptioin() {
		return createHttpResponse(HttpStatus.FORBIDDEN, ConstantVariables.NOT_ENOUGH_PERMISSION);
	}
	
	
	@ExceptionHandler(LockedException.class)
	public ResponseEntity<HttpResponse> lockedExceptioin() {
		return createHttpResponse(HttpStatus.UNAUTHORIZED, ConstantVariables.ACCOUNT_LOCKED);
	}
	
	

	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<HttpResponse> tokenExpiredExceptioin(TokenExpiredException exception) {
		return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage().toUpperCase());
	}
	
	
	@ExceptionHandler(EmailExistException.class)
	public ResponseEntity<HttpResponse> emailExistExceptioin(EmailExistException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	
	
	@ExceptionHandler(UserNameExistException.class)
	public ResponseEntity<HttpResponse> userNameExistExceptioin(UserNameExistException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<HttpResponse> emailNotFoundExceptioin(EmailNotFoundException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<HttpResponse> userNotFoundExceptioin(UserNotFoundException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public ResponseEntity<HttpResponse> noHandlerExceptioin(NoHandlerFoundException exception) {
//		return createHttpResponse(HttpStatus.BAD_REQUEST, "This page not found");
//	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<HttpResponse> methodNotSupportedExceptioin(HttpRequestMethodNotSupportedException exception) {
		HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods().iterator().next());
		return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(ConstantVariables.METHOD_IS_NOT_ALLOWED, supportedMethod));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerErrorExceptioin(Exception exception) {
		LOG.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,ConstantVariables.INTERNAL_SERVER_ERROR_MSG);
	}
	

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> noResultExceptioin(NoResultException exception) {
		return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	

	@ExceptionHandler(IOException.class)
	public ResponseEntity<HttpResponse> iOExceptioin(IOException exception) {
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}

	
	
	

	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
		return new ResponseEntity<HttpResponse>(new HttpResponse(new Date(), httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()), httpStatus);
	}

	
	@RequestMapping(ConstantVariables.ERROR_PATH)
	public ResponseEntity<HttpResponse> notFound404()
	{
		return createHttpResponse(HttpStatus.NOT_FOUND, "there is no mapping for this url");
	}
	
	
	
	//this method will handle once error accored and we can send to different url it basically gives the error path.
	@Override
	public String getErrorPath() {
		
		return ConstantVariables.ERROR_PATH;
	}

}
