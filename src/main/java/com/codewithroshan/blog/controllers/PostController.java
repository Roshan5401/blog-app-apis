package com.codewithroshan.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithroshan.blog.payloads.ApiResponse;
import com.codewithroshan.blog.payloads.PostDto;
import com.codewithroshan.blog.payloads.PostResponse;
import com.codewithroshan.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	PostService postService;
	@PostMapping("/userId/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId)
	{
		PostDto createdPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	@PutMapping("/updatepost/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId)
	{
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//url for pagination
	//http://localhost:9090/posts?pageSize=5&pageNumber=2&sortby=title
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber",defaultValue ="0",required=false)Integer pageNumber,@RequestParam(value="pageSize",defaultValue ="5",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = "postId",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue = "asc",required=false)String sortDir)
	{
		PostResponse s=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(s,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId)
	{
		PostDto s=this.postService.getPostbyId(postId);
		return new ResponseEntity<PostDto>(s,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId)
	{
		List<PostDto> s=this.postService.getAllPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(s,HttpStatus.OK);
	}
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId)
	{
		List<PostDto> s=this.postService.getAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(s,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/userId/{userId}/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByUserAndCategory(@PathVariable("userId") Integer userId,@PathVariable("categoryId") Integer categoryId)
	{
		List<PostDto> s=this.postService.getAllPostByUserAndCategory(userId,categoryId);
		return new ResponseEntity<List<PostDto>>(s,HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true),HttpStatus.OK);
	}
	
	//searching
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword")String keyword)
	{
		List<PostDto> posts=this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
}
