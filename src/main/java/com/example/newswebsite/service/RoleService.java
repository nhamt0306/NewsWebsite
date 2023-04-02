package com.example.newswebsite.service;



import com.example.newswebsite.entity.RoleName;
import com.example.newswebsite.entity.RoleEntity;

import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findByName(RoleName name);
}
