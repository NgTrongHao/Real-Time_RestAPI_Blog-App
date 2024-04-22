package com.nguyentronghao.realtime_restapi_blogapp.exception.factory.implement;

import com.nguyentronghao.realtime_restapi_blogapp.exception.factory.HttpStatusFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class HttpStatusFactoryImpl implements HttpStatusFactory {
    
    /**
     * Determines the HttpStatus for the exception.
     * If the exception is annotated with @ResponseStatus, returns the status code from the annotation.
     * Otherwise, returns HttpStatus.INTERNAL_SERVER_ERROR.
     *
     * @param exception  The handled exception
     * @return           The corresponding HttpStatus
     */
    public HttpStatus getHttpStatus(Exception exception) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
        return responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
