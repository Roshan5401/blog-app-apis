package com.codewithroshan.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithroshan.blog.entities.Role;
import com.codewithroshan.blog.entities.User;
import com.codewithroshan.blog.payloads.UserDto;
import com.codewithroshan.blog.repositories.RoleRepo;
import com.codewithroshan.blog.repositories.UserRepo;
import com.codewithroshan.blog.services.UserService;
import com.codewithroshan.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordencoder;
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto user) {
		User u=this.dtoToUser(user);
		User savedUser=this.userRepo.save(u);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		UserDto userDto=this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users=this.userRepo.findAll();
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());

					//OR
//	    List<UserDto> userDto=new ArrayList<>();
//		for(int i=0;i<user.size();i++)
//		{
//			UserDto u=this.userToDto(user.get(i));
//			userDto.add(u);
//		}
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		this.userRepo.delete(user);
	}
	
	public User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto,User.class);
		
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	public UserDto userToDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}
	@Override
	public UserDto registerNewUser(UserDto userdto) {
		User user=this.modelMapper.map(userdto, User.class);
		user.setPassword(this.passwordencoder.encode(user.getPassword()));
		//1 for admin, 2 for user
		Role role=this.roleRepo.findById(2).get();
		user.getRoles().add(role);
		User newUser=this.userRepo.save(user);
		return this.modelMapper.map(newUser,UserDto.class);
	}

}
