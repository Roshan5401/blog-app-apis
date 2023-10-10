package com.codewithroshan.blog.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithroshan.blog.config.UserInfoUserDetails;
import com.codewithroshan.blog.entities.User;
import com.codewithroshan.blog.exceptions.ResourceNotFoundException;
import com.codewithroshan.blog.repositories.UserRepo;

@Service
public class UserInfoUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException
	{
		
		//loading user from database  by username
		Optional<User> userInfo = this.userRepo.findByEmail(username);
		//converting the user to userdetails and maping the imp things
		return userInfo.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}
}
