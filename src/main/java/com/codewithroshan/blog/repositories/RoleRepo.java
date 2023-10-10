package com.codewithroshan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithroshan.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{

}
