package com.codewithroshan.blog.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithroshan.blog.payloads.JwtAuthRequest;
import com.codewithroshan.blog.payloads.UserDto;
import com.codewithroshan.blog.security.JwtService;
import com.codewithroshan.blog.security.UserInfoUserDetailsService;
import com.codewithroshan.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserInfoUserDetailsService userDetails;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
//	 @GetMapping("/all")
//	    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	    public List<Product> getAllTheProducts() {
//	        return service.getProducts();
//	    }
//
//	    @GetMapping("/{id}")
//	    @PreAuthorize("hasAuthority('ROLE_USER')")
//	    public Product getProductById(@PathVariable int id) {
//	        return service.getProduct(id);
//	    }

	
	@PostMapping("/login")
	public String createToken(@RequestBody JwtAuthRequest authRequest)
	{
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createdUser=this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(createdUser,HttpStatus.CREATED);
	}
}
