package com.codewithroshan.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;
import com.codewithroshan.blog.entities.Role;
import com.codewithroshan.blog.repositories.RoleRepo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@OpenAPIDefinition(
		info=@Info(
				title="Blogging-Backend-Website",
				version="1.0.0",
				description="This project is only for backend demostrating a blogging website",
//				termsOfService="runcodenow",
				contact=@Contact(
						name="Roshan kumar singh",
						email="roshansingh.ns@gmail.com"
						)
//				license=@License(
//					name="license",
//					url="runcodenow"
//				)
		)
	)

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	//implements CommandLineRunner added to make the run() function work
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	//bean : used by spring container to create it object automatically and provide for use
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println(this.passwordEncoder.encode("15"));
//		System.out.println(this.passwordEncoder.encode("1234"));
//	}
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public void run(String... args)throws Exception{
		Role role1=new Role();
		role1.setId(1);
		role1.setName("ROLE_ADMIN");
		Role role2=new Role();
		role2.setId(2);
		role2.setName("ROLE_USER");
		List<Role> roles=List.of(role1,role2);
		List<Role> r=this.roleRepo.saveAll(roles);
	}
}
