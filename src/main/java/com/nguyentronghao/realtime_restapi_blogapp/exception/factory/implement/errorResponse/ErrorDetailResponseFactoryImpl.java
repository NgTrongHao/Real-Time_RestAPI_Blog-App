package com.nguyentronghao.realtime_restapi_blogapp.exception.factory.implement.errorResponse;

import com.nguyentronghao.realtime_restapi_blogapp.exception.factory.ErrorDetailResponseFactory;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.ErrorDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Component
public class ErrorDetailResponseFactoryImpl implements ErrorDetailResponseFactory {
    
    /**
     * Creates an ErrorDetail object containing detailed information about the exception.
     *
     * @param exception   The thrown exception
     * @param webRequest  The original WebRequest
     * @return            The ErrorDetail object containing error information
     */
    public ErrorDetail getErrorResponse(Exception exception, WebRequest webRequest) {
        return new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }
    
}
