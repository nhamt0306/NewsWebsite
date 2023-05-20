package com.example.newswebsite.controller;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.dto.user.ChangePasswordDTO;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.form.user.UpdateInfoForm;
import com.example.newswebsite.mapper.UserMapper;
import com.example.newswebsite.security.principal.UserDetailService;
import com.example.newswebsite.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //Get all user
    @GetMapping("admin/users/getAllUser")
    public ResponseEntity<?> getAllUser(){
        List<UserEntity> userEntityList = userService.findAll();
        List<UserMapper> userMappers = new ArrayList<>();
        for (UserEntity user: userEntityList)
        {
            if (!user.getId().equals(userDetailService.getCurrentUser().getId())) {
                UserMapper userMapper = new UserMapper(user.getId(), user.getFullname(), user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail(), user.getAddress(), user.getGender(), user.getDob(), user.getStatus());
                userMapper.setAvatar(user.getAvatar());
                userMappers.add(userMapper);
            }
        }
        return ResponseEntity.ok(userMappers);
    }


    //Get current user
    @GetMapping("/user/profile")
    public ResponseEntity<?> getCurUser(){
        UserEntity user = userDetailService.getCurrentUser();
        UserMapper userMapper = new UserMapper(user.getId(), user.getFullname(), user.getUsername(), null, user.getPhone(), user.getEmail(), user.getAddress(), user.getGender(), user.getDob(), user.getStatus());
        userMapper.setAvatar(user.getAvatar());
        return ResponseEntity.ok(userMapper);
    }

    @GetMapping("admin/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id){
        try {
            UserEntity user = userService.findById(id).get();
            UserMapper userMapper = new UserMapper(user.getId(), user.getFullname(), user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail(), user.getAddress(), user.getGender(), user.getDob(), user.getStatus());
            userMapper.setAvatar(user.getAvatar());
            return ResponseEntity.ok(userMapper);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindUser + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/profile/change")
    public Object changeProfile(@RequestBody UpdateInfoForm userEntity) throws ParseException {
        UserEntity user = userDetailService.getCurrentUser();
        user.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        if (userEntity.getAddress() != null)
        {
            user.setAddress(userEntity.getAddress());
        }
        if (userEntity.getFullname() != null)
        {
            user.setFullname(userEntity.getFullname());
        }
//        if (userEntity.getDob() != null)
//        {
//            user.setDob(userEntity.getDob());
//        }
        if (userEntity.getGender() != null)
        {
            user.setGender(userEntity.getGender());
        }
        if (userEntity.getPhone() != null)
        {
            user.setPhone(userEntity.getPhone());
        }
        userService.save(user);
        return "Change user profile success!";
    }

    @PostMapping("/user/changepassword")
    public Object changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws ParseException {
        UserEntity user = userDetailService.getCurrentUser();
        if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRePassword()))
        {
            if (passwordEncoder.matches(changePasswordDTO.getCurPassword(), user.getPassword()))
            {
                user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userService.save(user);
            }
            else
            {
                return new ResponseEntity<>("Password incorrect", HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>("Confirm-password is incorrect!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Changed password successfully", HttpStatus.OK);
    }

    @PostMapping("admin/users/uprole")
    public ResponseEntity<?> upRoleUser(@RequestParam(value = "email", required = false) String email){
        String username = userService.findByEmail(email).get().getUsername();
        if (userService.upRole(email)){
            return ResponseEntity.ok("Update Role User "+username+" Success");
        }
        return ResponseEntity.ok("User "+username+" already have this role!!!");
    }

    @PostMapping("admin/users/downrole")
    public ResponseEntity<?> downRoleUser(@RequestParam(value = "email", required = false) String email){
        String username = userService.findByEmail(email).get().getUsername();
        if (userService.downRole(email)){
            return ResponseEntity.ok("Update Role User "+username+" Success");
        }
        return ResponseEntity.ok("User "+username+" already have this role!!!");
    }

    @DeleteMapping("admin/users/delete")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "email", required = false) String email)
    {
        if (userService.existsByEmail(email))
        {
            String username = userService.findByEmail(email).get().getUsername();
            Long userId = userService.findByEmail(email).get().getId();
            userService.deleteById(userId);
            return ResponseEntity.ok("Delete "+username+" success!");
        }
        return ResponseEntity.ok("Username unavailable!");
    }

    @PostMapping("admin/users/ban")
    public ResponseEntity<?> banUser(@RequestParam(value = "email", required = false) String email)
    {
        if (userService.existsByEmail(email))
        {
            UserEntity user = userService.findByEmail(email).get();
            user.setStatus("Disable");
            userService.save(user);
            return ResponseEntity.ok("Ban "+user.getFullname()+" success!");
        }
        return ResponseEntity.ok("Username unavailable!");
    }

    @PostMapping("admin/users/unban")
    public ResponseEntity<?> unbanUser(@RequestParam(value = "email", required = false) String email)
    {
        if (userService.existsByEmail(email))
        {
            UserEntity user = userService.findByEmail(email).get();
            if(user.getStatus().equals("Disable"))
            {
                user.setStatus("Active");
                userService.save(user);
            }
            return ResponseEntity.ok("Unban "+user.getFullname()+" success!");
        }
        return ResponseEntity.ok("Username unavailable!");
    }
}
