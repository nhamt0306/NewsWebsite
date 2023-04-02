package com.example.newswebsite.controller;

import com.example.newswebsite.entity.RoleEntity;
import com.example.newswebsite.entity.RoleName;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.form.SignInForm;
import com.example.newswebsite.form.SignUpForm;
import com.example.newswebsite.mapper.JwtResponse;
import com.example.newswebsite.mapper.ResponseMessage;
import com.example.newswebsite.security.jwt.JwtProvider;
import com.example.newswebsite.security.principal.UserPrinciple;
import com.example.newswebsite.service.impl.RoleServiceImpl;
import com.example.newswebsite.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*")   //Để ghép AuthController với các controller khác
@RequestMapping
@RestController
public class AuthenticationController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpForm signUpForm){
        if (userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("Tên tài khoản đã tồn tại! Vui lòng thử lại", "false"), HttpStatus.CONFLICT);
        }
        if (userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("Email đã tồn tại! Vui lòng thử lại","false"), HttpStatus.CONFLICT);
        }


        UserEntity user = new UserEntity(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), signUpForm.getPhonenumber(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRole =signUpForm.getRoles();
        Set<RoleEntity> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role){
                case "ADMIN":
                    RoleEntity adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()-> new RuntimeException("Role không hợp lệ!")
                    );
                    roles.add(adminRole);
                    break;
                default:
                    RoleEntity userRole = roleService.findByName(RoleName.USER).orElseThrow(
                            ()-> new RuntimeException("Role không hợp lệ!")
                    );
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Tạo user thành công!"), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm){
        // UsernamePasswordAuthenticationToken sẽ kiểm tra thông tin người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
        );
        // Set token lên hệ thống
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Tạo jwt token
        String token = jwtProvider.createToken(authentication.getName());
        // Lấy ra thông tin người dùng hiện tại trên hệ thống
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        // Trả về kết quả.
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getId(), userPrinciple.getAuthorities(), userPrinciple.getAvatar()));
    }

}
