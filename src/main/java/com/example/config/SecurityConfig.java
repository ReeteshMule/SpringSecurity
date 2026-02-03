package com.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtFilter jwtFilter; 
	//HttpSecurity is the type of resquest which will return the object of the SecurityFilterChain object by using build method
	@Bean
	public SecurityFilterChain sfc(HttpSecurity hs) throws Exception {
//		dislable csrf using lambda systax
		hs.csrf(customizer -> customizer.disable());
		
//		login from autorization
//		hs.authorizeHttpRequests(request -> request.anyRequest().authenticated());
	
//		enable login from for crom web browser
//		hs.formLogin(Customizer.withDefaults());
	
//		enable login from for another user such as postman and other 
//		hs.httpBasic(Customizer.withDefaults());
		
//		making _csrf as the state less which means that we want request validation and dont want to woeeay about the session id
//		hs.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		return hs.csrf(customizer -> customizer.disable())
		.authorizeHttpRequests(request -> request
				.requestMatchers("register","login") // for that url don't asked for authencation
				.permitAll()
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		.build();
//		imperative way
//		Customizewr is the type of Security web pakage and it is the Functionalinterface and the method name is 
//		Customizer<CsrfConfigurer<HttpSecurity>> csrfCust = new Customizer<CsrfConfigurer<HttpSecurity>>;
//		hs.csrf();

	}
	
// stateless means the server dont no anything about you between requests everything we want to  ittrate manually
//	@Bean
//	public UserDetailsService uds() {
//		UserDetails user1 = User.withDefaultPasswordEncoder()
//				.username("Akash")
//				.password("A@123")
//				.roles("user")
//				.build();
//		UserDetails user2 = User.withDefaultPasswordEncoder()
//				.username("Ashish")
//				.password("Ashish@123")
//				.roles("admin")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1,user2);
//		
//	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(userDetailService);
//	    provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // testing only
	    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
	    return provider;
	}
	
	@Bean
	public AuthenticationManager am(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}



