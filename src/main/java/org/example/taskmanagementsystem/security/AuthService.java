package org.example.taskmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.user.UserRs;
import org.example.taskmanagementsystem.dto.user.UserToUserRsConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserToUserRsConverter userToUserRsConverter;


    public Map<String, Object> createLoginInfo(Authentication authentication) {
        Map<String, Object> loginInfo = new HashMap<>();
        AppUserDetails principal = (AppUserDetails) authentication.getPrincipal();
        UserRs userRs = userToUserRsConverter.convert(principal.getUser());
        String token = jwtProvider.createToken(authentication);

        loginInfo.put("userInfo", userRs);
        loginInfo.put("token", token);

        return loginInfo;



    }
}
