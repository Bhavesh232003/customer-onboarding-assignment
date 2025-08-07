package com.Assignment.customer_onboarding.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ADMIN_TOKEN = "ADMIN123";
    private static final String USER_TOKEN = "USER123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(AUTH_HEADER);

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            String token = header.substring(TOKEN_PREFIX.length());
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            // If the token is valid, set the user's authentication info in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (ADMIN_TOKEN.equals(token)) {
            return new UsernamePasswordAuthenticationToken(null, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        if (USER_TOKEN.equals(token)) {
            return new UsernamePasswordAuthenticationToken(null, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }
        return null;
    }
}
