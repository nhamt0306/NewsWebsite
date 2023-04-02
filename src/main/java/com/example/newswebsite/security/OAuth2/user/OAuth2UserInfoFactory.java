package com.example.newswebsite.security.OAuth2.user;



import com.example.newswebsite.security.OAuth2.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            System.out.println("login with google");
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new RuntimeException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
