package com.codewithroshan.blog.services;

import java.util.List;

import com.codewithroshan.blog.payloads.PostDto;
import com.codewithroshan.blog.payloads.PostResponse;

public interface PostService {
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	//delete
	void deletePost(Integer postId);
	//get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	//get single post
	PostDto getPostbyId(Integer postId);
	//get all post by category
	List<PostDto> getAllPostByCategory(Integer categoryId);
	//get all post by user
	List<PostDto> getAllPostByUser(Integer UserId);
	//get post by user and category
	List<PostDto> getAllPostByUserAndCategory(Integer UserId,Integer categoryId);
	//search post
	List<PostDto> searchPost(String keyword);
	
}
