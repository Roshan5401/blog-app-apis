package com.codewithroshan.blog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.codewithroshan.blog.entities.Category;
import com.codewithroshan.blog.entities.Post;
import com.codewithroshan.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByUserAndCategory(User user,Category category);
	Optional<Post> findById(Integer postId);
	List<Post> findByTitleContaining(String title);
	//using this way we can write custom query
//	@Query("SELECT p FROM Post p WHERE p.user = :user AND p.category = :category")
//    List<Post> findByUserAndCategory(@Param("user") User user, @Param("category") Category category);

}
