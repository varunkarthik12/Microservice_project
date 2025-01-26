package com.example.APIGateway.Filters;

import com.example.APIGateway.Service.CustomUserDetailsService;
import com.example.APIGateway.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private String username;

    private String jwt;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
            if(request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer "))
            {
                jwt = request.getHeader("Authorization").substring(7);

                username = jwtUtils.extractUserName(jwt);

                UserDetails user = service.loadUserByUsername(username);

                if(jwtUtils.validateToken(jwt,user) && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),jwtUtils.extract_roles(jwt));

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request,response);
        }

    }

