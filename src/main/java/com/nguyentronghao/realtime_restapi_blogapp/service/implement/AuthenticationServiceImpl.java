package com.nguyentronghao.realtime_restapi_blogapp.service.implement;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.JwtAuthResponse;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.user.AuthRequestDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.user.RegisterDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.entity.User;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.DuplicateException;
import com.nguyentronghao.realtime_restapi_blogapp.repository.RoleRepository;
import com.nguyentronghao.realtime_restapi_blogapp.repository.UserRepository;
import com.nguyentronghao.realtime_restapi_blogapp.service.AuthenticationService;
import com.nguyentronghao.realtime_restapi_blogapp.service.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder,
                                     ModelMapper modelMapper,
                                     JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public JwtAuthResponse login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDto.getUsernameOrEmail(), authRequestDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtAuthResponse(jwtTokenProvider.generateToken(authentication));
    }
    
    @Override
    public String register(RegisterDto registerDto) {
        if (!checkUsernameOrEmailExists(registerDto)) {
            User user = convertDtoToUser(registerDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
            userRepository.save(user);
            return "Registered successfully";
        }
        return "Registration Failed";
    }
    
    private boolean checkUsernameOrEmailExists(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new DuplicateException("Username is already in use");
        } else if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new DuplicateException("Email is already in use");
        } else {
            return false;
        }
    }
    
    private User convertDtoToUser(RegisterDto registerDto) {
        return modelMapper.map(registerDto, User.class);
    }
}
