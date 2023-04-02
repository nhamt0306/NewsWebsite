package com.example.newswebsite.service.impl;


import com.example.newswebsite.entity.RoleEntity;
import com.example.newswebsite.entity.RoleName;
import com.example.newswebsite.repository.RoleRepository;
import com.example.newswebsite.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<RoleEntity> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
