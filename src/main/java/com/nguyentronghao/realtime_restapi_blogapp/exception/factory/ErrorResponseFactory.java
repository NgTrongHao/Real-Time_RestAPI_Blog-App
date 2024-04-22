package com.nguyentronghao.realtime_restapi_blogapp.exception.factory;

import org.springframework.web.context.request.WebRequest;

public interface ErrorResponseFactory<T> {
    T getErrorResponse(Exception exception, WebRequest webRequest);
}
