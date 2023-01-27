package com.myproject.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
