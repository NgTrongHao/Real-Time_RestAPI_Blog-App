package com.nguyentronghao.realtime_restapi_blogapp.service;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.JwtAuthResponse;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.user.AuthRequestDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.user.RegisterDto;

public interface AuthenticationService {
    
    JwtAuthResponse login(AuthRequestDto authRequestDto);
    
    String register(RegisterDto registerDto);
}
