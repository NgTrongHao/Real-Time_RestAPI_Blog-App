package com.nguyentronghao.realtime_restapi_blogapp.exception.handler;

import com.nguyentronghao.realtime_restapi_blogapp.model.error.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Component
public class AccessDeniedExceptionHandler {
    
    public ResponseEntity<ErrorDetail> handleException(AccessDeniedException accessDeniedException,
                                                       WebRequest webRequest) {
        return new ResponseEntity<>(getErrorDetail(accessDeniedException, webRequest), HttpStatus.FORBIDDEN);
    }
    
    private ErrorDetail getErrorDetail(Exception exception, WebRequest webRequest) {
        return new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }
}
