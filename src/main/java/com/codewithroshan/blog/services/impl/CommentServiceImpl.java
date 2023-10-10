package com.codewithroshan.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithroshan.blog.entities.Comment;
import com.codewithroshan.blog.entities.Post;
import com.codewithroshan.blog.entities.User;
import com.codewithroshan.blog.exceptions.ResourceNotFoundException;
import com.codewithroshan.blog.payloads.CommentDto;
import com.codewithroshan.blog.repositories.CommentRepo;
import com.codewithroshan.blog.repositories.PostRepo;
import com.codewithroshan.blog.repositories.UserRepo;
import com.codewithroshan.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment=this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		this.commentRepo.delete(comment);
	}

}
