package com.codewithroshan.blog.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithroshan.blog.entities.Category;
import com.codewithroshan.blog.entities.Post;
import com.codewithroshan.blog.entities.User;
import com.codewithroshan.blog.exceptions.ResourceNotFoundException;
import com.codewithroshan.blog.payloads.CategoryDto;
import com.codewithroshan.blog.payloads.PostDto;
import com.codewithroshan.blog.payloads.PostResponse;
import com.codewithroshan.blog.repositories.CategoryRepo;
import com.codewithroshan.blog.repositories.PostRepo;
import com.codewithroshan.blog.repositories.UserRepo;
import com.codewithroshan.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		Post post=this.modelmapper.map(postDto, Post.class);
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		return this.modelmapper.map(newPost,PostDto.class);
	}

//	@SuppressWarnings("deprecation")
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Post p=this.modelmapper.map(postDto, Post.class);
		post.setContent(p.getContent());
		post.setImageName(p.getImageName());
		post.setTitle(p.getTitle());
		post.setAddedDate(new Date());
		Post saved=this.postRepo.save(post);
		return this.modelmapper.map(saved,PostDto.class);
//		post.setContent();
//		Optional<Post> post=this.postRepo.findById(postId);
//		Post post = postOptional.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
	}

	@Override
	public void deletePost(Integer postId) {
		Post p=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		this.postRepo.delete(p);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
			sort=Sort.by(sortBy).ascending();
		else
			sort=Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> gotpost=pagePost.getContent();
		List<PostDto> l=new ArrayList<>();
		for(int i=0;i<gotpost.size();i++)
		{
			Post c=gotpost.get(i);
			l.add(this.modelmapper.map(c,PostDto.class));
		}
		PostResponse pr=new PostResponse();
		pr.setContent(l);
		pr.setPageNumber(pagePost.getNumber());
		pr.setPageSize(pagePost.getSize());
		pr.setTotalPages(pagePost.getTotalPages());
		pr.setTotalElement(pagePost.getTotalElements());
		pr.setLastPage(pagePost.isLast());
		return pr;
	}

	@Override
	public PostDto getPostbyId(Integer postId) {
		// TODO Auto-generated method stub
		Post p=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));;
		PostDto gotpost=this.modelmapper.map(p, PostDto.class);
		return gotpost;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		List<Post> gotpost=this.postRepo.findByCategory(cat);
		List<PostDto> l=new ArrayList<>();
		for(int i=0;i<gotpost.size();i++)
		{
			Post c=gotpost.get(i);
			l.add(this.modelmapper.map(c,PostDto.class));
		}
		return l;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer UserId) {
		User user=this.userRepo.findById(UserId).orElseThrow(()->new ResourceNotFoundException("User","UserId",UserId));
		List<Post> gotpost=this.postRepo.findByUser(user);
		List<PostDto> l=new ArrayList<>();
		for(int i=0;i<gotpost.size();i++)
		{
			Post c=gotpost.get(i);
			l.add(this.modelmapper.map(c,PostDto.class));
		}
		return l;
	}

	@Override
	public List<PostDto> getAllPostByUserAndCategory(Integer userId, Integer categoryId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		List<Post> gotpost=this.postRepo.findByUserAndCategory(user, category);
		List<PostDto> l=new ArrayList<>();
		for(int i=0;i<gotpost.size();i++)
		{
			Post c=gotpost.get(i);
			l.add(this.modelmapper.map(c,PostDto.class));
		}
		return l;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=new ArrayList<>();
		for(int i=0;i<posts.size();i++)
		{
			Post c=posts.get(i);
			postDtos.add(this.modelmapper.map(c,PostDto.class));
		}
		
		return postDtos;
	}

}
