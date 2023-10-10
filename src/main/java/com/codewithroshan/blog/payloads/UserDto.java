package com.codewithroshan.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import com.codewithroshan.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min=4,message = "Username must be of length 4")
	private String name;
	@Email(message="Email is invalid")
	private String email;
	@NotEmpty
	@Size(min=3,max=10,message="password must be of 3 to 10 characters")
	private String password;
	@NotEmpty
	@Size(min=3,message = "About must be of length 3")
	private String about;
	private List<RoleDto> roles=new ArrayList<>();
}
