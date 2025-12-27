package com.ride.goeasy.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getServletPath().startsWith("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = jwtUtils.extractUsername(token);
			} catch (Exception e) {
				System.out.println("Invalid JWT: " + e.getMessage());
			}
		}

		// Validate token and set authentication context
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			String role = jwtUtils.extractRole(token); // ✅ Extract role claim (ADMIN/USER)

			if (jwtUtils.isTokenValid(token, username)) {
				var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						authorities);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}