package com.nguyentronghao.realtime_restapi_blogapp.exception.handler;

import com.nguyentronghao.realtime_restapi_blogapp.model.error.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Component
public class BadCredentialsExceptionHandler {
    
    public ResponseEntity<ErrorDetail> handleException(BadCredentialsException badCredentialsException,
                                                       WebRequest webRequest) {
        return new ResponseEntity<>(getErrorDetail(badCredentialsException, webRequest), HttpStatus.UNAUTHORIZED);
    }
    
    private ErrorDetail getErrorDetail(Exception exception, WebRequest webRequest) {
        return new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }
}
