package com.nguyentronghao.realtime_restapi_blogapp.controller;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.JwtAuthResponse;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.user.AuthRequestDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.user.RegisterDto;
import com.nguyentronghao.realtime_restapi_blogapp.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth", headers = "X-API-VERSION=1")
@Tag(
        name = "Rest APIs for User Authentication Resource",
        description = "Rest APIs for User Authentication Resource"
)
public class AuthController {
    private final AuthenticationService authenticationService;
    
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    @PostMapping(value = {"/signup", "/register"})
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterDto registerDto) {
        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.CREATED);
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return new ResponseEntity<>(authenticationService.login(authRequestDto), HttpStatus.OK);
    }
}
