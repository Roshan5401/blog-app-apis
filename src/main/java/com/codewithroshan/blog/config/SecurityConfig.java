package com.codewithroshan.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithroshan.blog.security.JwtAuthFilter;

import com.codewithroshan.blog.security.UserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig{
	@Autowired
    private JwtAuthFilter authFilter;
//	@Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter(){
//        return  new JwtAuthenticationFilter();
//    }
	@Bean
    public UserDetailsService userDetailsService() {
		//called inside the dao function
        return new UserInfoUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	//for password encryption
        return new BCryptPasswordEncoder();
    }
//Basic security without Jwt
//    @Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//    	//ye csrf ko diable krega
//		//mind krega ki sb authorize ho rha h ya nhi
//		//html to javascript me convert krega page ko
//    	
//    	return httpSecurity
//    		        .csrf(csrf -> csrf.disable())
//    		        .authorizeHttpRequests(auth -> auth
//    		            .anyRequest().authenticated()
//    		        )
//    		        .httpBasic(Customizer.withDefaults())
//    		        .build();
//	}
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
        		.authorizeHttpRequests(
        				authorizeRequests ->
                        authorizeRequests
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            .requestMatchers("/api/v1/auth/register").permitAll()
                            .requestMatchers(HttpMethod.GET).permitAll()
                            .requestMatchers("/v3/api-docs/**",
                            		"/v2/api-docs/**",
                                    "/v3/api-docs.yaml",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/swagger-resouces/**",
                                    "/webjars/**").permitAll()
                            .anyRequest().authenticated()
        				)
        		.sessionManagement(sessionManagement ->
                			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        		)
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
