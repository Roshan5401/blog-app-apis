package com.codewithroshan.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private int id;
	private String content;
	private UserDto user;
}