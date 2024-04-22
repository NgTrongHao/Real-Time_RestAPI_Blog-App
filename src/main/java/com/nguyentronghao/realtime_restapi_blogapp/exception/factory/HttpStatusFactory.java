package com.nguyentronghao.realtime_restapi_blogapp.exception.factory;

import org.springframework.http.HttpStatus;

public interface HttpStatusFactory {
    HttpStatus getHttpStatus(Exception e);
}
