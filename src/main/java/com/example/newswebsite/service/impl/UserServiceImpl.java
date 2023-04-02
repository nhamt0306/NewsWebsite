package com.example.newswebsite.service.impl;

import com.example.newswebsite.dto.response.ResourceNotFoundException;
import com.example.newswebsite.entity.RoleEntity;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.repository.RoleRepository;
import com.example.newswebsite.repository.UserRepository;
import com.example.newswebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // up role user to admin
    @Override
    public Boolean upRole(String email) {
        // Get user
        Optional<UserEntity> curUser = userRepository.findByEmail(email);
        // Get admin role
        Optional<RoleEntity> roleAdmin = roleRepository.findById(Long.parseLong("1"));
        // Get role cur user
        Set<RoleEntity> roleUser = curUser.get().getRoles();
        // Check if role's user not contain "admin" role --> user only have role "user"
        if (!roleUser.contains(roleAdmin.get()))
        {
            roleUser.add(roleAdmin.get());
            curUser.get().setRoles(roleUser);
            curUser.get().setUpdate_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(curUser.get());
            return true;
        }
        return false;
    }

    // down role admin to user
    @Override
    public Boolean downRole(String email) {
        // Get user
        if (!existsByEmail(email))
        {
            return false;
        }
        Optional<UserEntity> curUser = userRepository.findByEmail(email);
        // Get admin role
        Optional<RoleEntity> roleAdmin = roleRepository.findById(Long.parseLong("1"));
        // Get role cur user
        Set<RoleEntity> roleUser = curUser.get().getRoles();
        // Check if role's user not contain "admin" role --> user only have role "user"
        if (roleUser.contains(roleAdmin.get()))
        {
            roleUser.remove(roleAdmin.get());
            curUser.get().setRoles(roleUser);
            curUser.get().setUpdate_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(curUser.get());
            return true;
        }
        return false;
    }

    @Override
    public UserEntity uploadAvatar(MultipartFile image, Long id) {
        UserEntity user = userRepository.findById(id).get();
        String imageUrl = cloudinaryService.uploadFile(image,String.valueOf(user.getId()),
                "ClothingStore"+ "/" + "Avatar");
        if(!imageUrl.equals("-1")) {
            user.setAvatar(imageUrl);
        }
        else if(user.getAvatar().equals("") || user.getAvatar().equals("-1"))
            user.setAvatar("");

        return userRepository.save(user);
    }

    @Override
    public UserEntity changeUserPasswordByEmail(String email, String newPassword) {
        UserEntity userChangePassword = userRepository.findByEmail(email)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Cannot found user with email = " + email));
        return userChangePassword;
    }
}
