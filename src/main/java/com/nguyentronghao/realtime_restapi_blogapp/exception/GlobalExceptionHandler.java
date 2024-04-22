package com.nguyentronghao.realtime_restapi_blogapp.exception;

import com.nguyentronghao.realtime_restapi_blogapp.exception.handler.*;
import com.nguyentronghao.realtime_restapi_blogapp.exception.factory.ErrorDetailResponseFactory;
import com.nguyentronghao.realtime_restapi_blogapp.exception.factory.HttpStatusFactory;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.DuplicateException;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.ErrorDetail;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private final HttpStatusFactory httpStatusFactory;
    private final ErrorDetailResponseFactory errorDetailResponseFactory;
    private final MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler;
    private final ResourceNotFoundExceptionHandler resourceNotFoundExceptionHandler;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;
    private final DuplicateExceptionHandler duplicateExceptionHandler;
    private final BadCredentialsExceptionHandler badCredentialsExceptionHandler;
    
    public GlobalExceptionHandler(HttpStatusFactory httpStatusFactory,
                                  ErrorDetailResponseFactory errorDetailResponseFactory,
                                  MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler,
                                  ResourceNotFoundExceptionHandler resourceNotFoundExceptionHandler,
                                  AccessDeniedExceptionHandler accessDeniedExceptionHandler,
                                  DuplicateExceptionHandler duplicateExceptionHandler,
                                  BadCredentialsExceptionHandler badCredentialsExceptionHandler) {
        this.httpStatusFactory = httpStatusFactory;
        this.errorDetailResponseFactory = errorDetailResponseFactory;
        this.methodArgumentNotValidExceptionHandler = methodArgumentNotValidExceptionHandler;
        this.resourceNotFoundExceptionHandler = resourceNotFoundExceptionHandler;
        this.accessDeniedExceptionHandler = accessDeniedExceptionHandler;
        this.duplicateExceptionHandler = duplicateExceptionHandler;
        this.badCredentialsExceptionHandler = badCredentialsExceptionHandler;
    }
    
    /**
     * Handles any unhandled exceptions that are not specifically caught by other exception handlers.
     *
     * @param exception  The exception that occurred
     * @param webRequest The original WebRequest
     * @return           An HTTP response containing error details and status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleUnHandledException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(errorDetailResponseFactory.getErrorResponse(exception, webRequest), httpStatusFactory.getHttpStatus(exception));
    }
    
    /**
     * Handles MethodArgumentNotValidException.
     * Returns a ResponseEntity containing a Map of errors.
     *
     * @param exception The MethodArgumentNotValidException
     * @return ResponseEntity containing a Map of errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return methodArgumentNotValidExceptionHandler.handleException(exception);
    }
    
    /**
     * Handles the ResourceNotFoundException.
     *
     * @param exception   The ResourceNotFoundException thrown
     * @param webRequest  The original WebRequest
     * @return            An HTTP response containing error details and status code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        return resourceNotFoundExceptionHandler.handleException(exception, webRequest);
    }
    
    /**
     * Handles the AccessDeniedException.
     *
     * @param exception   The AccessDeniedException thrown
     * @param webRequest  The original WebRequest
     * @return            An HTTP response containing error details and status code
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
        return accessDeniedExceptionHandler.handleException(exception, webRequest);
    }
    
    /**
     * Handles the DuplicateException.
     *
     * @param exception   The DuplicateException thrown
     * @param webRequest  The original WebRequest
     * @return            An HTTP response containing error details and status code
     */
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleDuplicateException(DuplicateException exception, WebRequest webRequest) {
        return duplicateExceptionHandler.handleException(exception, webRequest);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleBadCredentialsException(BadCredentialsException exception, WebRequest webRequest) {
        return badCredentialsExceptionHandler.handleException(exception, webRequest);
    }
}
