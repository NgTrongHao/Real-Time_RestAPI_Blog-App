package com.nguyentronghao.realtime_restapi_blogapp.exception.handler;

import com.nguyentronghao.realtime_restapi_blogapp.exception.factory.ErrorDetailResponseFactory;
import com.nguyentronghao.realtime_restapi_blogapp.exception.factory.HttpStatusFactory;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.DuplicateException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class DuplicateExceptionHandler {
    private final ErrorDetailResponseFactory errorDetailResponseFactory;
    private final HttpStatusFactory httpStatusFactory;
    
    public DuplicateExceptionHandler(ErrorDetailResponseFactory errorDetailResponseFactory, HttpStatusFactory httpStatusFactory) {
        this.errorDetailResponseFactory = errorDetailResponseFactory;
        this.httpStatusFactory = httpStatusFactory;
    }
    
    public ResponseEntity<?> handleException(DuplicateException exception, WebRequest webRequest) {
        return new ResponseEntity<>(errorDetailResponseFactory.getErrorResponse(exception, webRequest), httpStatusFactory.getHttpStatus(exception));
    }
}
