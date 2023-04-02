package com.example.newswebsite.security.OAuth2;

import com.example.newswebsite.security.OAuth2.user.OAuth2UserInfo;
import com.example.newswebsite.security.OAuth2.user.OAuth2UserInfoFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        System.out.println("loadUser");
        try {
            OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

            OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
            if (oAuth2UserInfo.getEmail().isEmpty()) {
                throw new RuntimeException("Email not found from OAuth2 provider");
            }
            System.out.println(oAuth2User.getAttributes());

            return oAuth2User;
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            return null;
        }
    }
}
