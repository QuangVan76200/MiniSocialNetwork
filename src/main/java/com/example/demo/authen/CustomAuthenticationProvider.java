//package com.example.demo.authen;
//
//import java.util.ArrayList;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if (shouldAuthenticateAgainstThirdPartySystem()) {
//            return new UsernamePasswordAuthenticationToken(
//                    name, password, new ArrayList<>());
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//}
