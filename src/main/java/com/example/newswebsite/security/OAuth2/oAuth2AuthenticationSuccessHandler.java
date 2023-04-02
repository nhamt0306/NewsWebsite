package com.example.newswebsite.security.OAuth2;

import com.example.newswebsite.entity.RoleEntity;
import com.example.newswebsite.entity.RoleName;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.security.jwt.JwtProvider;
import com.example.newswebsite.service.impl.RoleServiceImpl;
import com.example.newswebsite.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class oAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final HttpCookiesOAuth2AuthorizationRequestRepository httpSessionOAuth2AuthorizationRequestRepository;

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String fullname = oAuth2User.getAttribute("name");
        String avatar = oAuth2User.getAttribute("picture");
        if(userService.findByEmail(email) == null)
        {
            // create account here
            UserEntity curUser = new UserEntity();
            curUser.setAvatar(avatar);
            curUser.setUsername(email);
            curUser.setFullname(fullname);
            curUser.setPassword(passwordEncoder.encode(email));

            Set<RoleEntity> roles = new HashSet<>();
            RoleEntity userRole = roleService.findByName(RoleName.USER).orElseThrow(
                    ()-> new RuntimeException("Role không hợp lệ!")
            );
            roles.add(userRole);
            curUser.setRoles(roles);
            userService.save(curUser);
        }

        String targetUrl = determineTargetUrl(request, response, authentication);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String token = JwtProvider.createToken(oAuth2User.getName());

        String userName = URLEncoder.encode(Objects.requireNonNull(oAuth2User.getAttribute("name")).toString(), StandardCharsets.UTF_8);

        // sua port sang 3000 --> ban thong tin token len url --> url nay get thong tin, sau do chuyen thong tin sang url trang home
        return UriComponentsBuilder.fromUriString("").host("localhost").port(8099).path("/login")
                .queryParam("userName", oAuth2User.getAttribute("email").toString())
//                .queryParam("email", (Object) oAuth2User.getAttribute("email"))
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpSessionOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
