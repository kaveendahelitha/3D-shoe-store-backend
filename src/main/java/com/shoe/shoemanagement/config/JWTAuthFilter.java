package com.shoe.shoemanagement.config;

import com.shoe.shoemanagement.Serviceuser.CustomUserDetailsService;
import com.shoe.shoemanagement.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    // Static variable to store the current user's username
    public static String CURRENT_USER = "";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // Add a check to bypass authentication for certain paths
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/forgotPassword") || requestPath.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
       // If the Authorization header is missing or does not start with "Bearer ", proceed without applying the filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        try {
            // Extract the username from the JWT token
            userEmail = jwtUtils.extractUsername(jwtToken);
            CURRENT_USER = userEmail; // Set the static variable with the current username
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
            filterChain.doFilter(request, response);
            return;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
            filterChain.doFilter(request, response);
            return;
        }
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            if (jwtUtils.isValidToken(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
