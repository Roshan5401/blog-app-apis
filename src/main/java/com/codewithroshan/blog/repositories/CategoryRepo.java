package com.codewithroshan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithroshan.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
