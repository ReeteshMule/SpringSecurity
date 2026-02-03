package com.example.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.servlet.function.ServerResponse.Context;

import com.example.service.JWTService;
import com.example.service.MyUserDeteilService;
//import com.example.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTService js;
	
	@Autowired
	ApplicationContext ac; // it return the beans of that repticular object

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// when we pass the token we get the 
		// barer token
		String authHeader = request.getHeader("Authorization");
		String Token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			Token = authHeader.substring(7);
			username = js.extractUserName(Token);
			
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails ud = 
					ac.getBean(MyUserDeteilService.class).loadUserByUsername(username);
			
			if(js.validateToken(Token,ud)) {
				UsernamePasswordAuthenticationToken authtoken =
						new UsernamePasswordAuthenticationToken(ud,null, ud.getAuthorities());
				authtoken.setDetails(authtoken);
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
	
	

}
