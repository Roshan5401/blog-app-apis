package com.codewithroshan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithroshan.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
