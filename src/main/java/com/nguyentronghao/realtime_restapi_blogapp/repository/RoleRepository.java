package com.nguyentronghao.realtime_restapi_blogapp.repository;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}