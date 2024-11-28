package com.example.TaskManagement.security;


import com.example.TaskManagement.security.jwt.JwtUtils;
import com.example.TaskManagement.security.service.RefreshTokenService;
import com.example.TaskManagement.dto.request.LoginRequest;
import com.example.TaskManagement.dto.request.RefreshTokenRequest;
import com.example.TaskManagement.dto.request.UserRequest;
import com.example.TaskManagement.dto.response.AuthResponse;
import com.example.TaskManagement.dto.response.RefreshTokenResponse;
import com.example.TaskManagement.exception.RefreshTokenException;
import com.example.TaskManagement.model.RefreshToken;
import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.service.interfaces.RoleService;
import com.example.TaskManagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;


    public AuthResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .userName(userDetails.getUsername())
                .email(userDetails.getEmail())
                .role(roles.get(0))
                .build();
    }

    public void register(UserRequest userRequest){
        var user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(roleService.findByRole(RoleType.valueOf(userRequest.getRole())))
                .build();

        userService.save(user);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        String requestRefreshToken = request.getRefreshToken();
        return  refreshTokenService.findByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getId)
                .map(userId -> {
                    User tokenUser = userService.findById(userId);
                    String token = jwtUtils.generateToken(tokenUser.getEmail());
                    return  new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException("Refresh token not found!"));
    }

    public void logout() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentPrincipal instanceof AppUserDetails userDetails){
            Long userId = userDetails.getId();

            refreshTokenService.deleteByUserId(userId);
        }
    }


}
