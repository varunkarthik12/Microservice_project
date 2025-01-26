package com.example.APIGateway.Filters;


import com.example.APIGateway.Service.CustomUserDetailsService;
import com.example.APIGateway.Utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class JwtRequestFilters extends AbstractGatewayFilterFactory<JwtRequestFilters.Config> {


    private String username;

    private String jwt;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService service;

    public JwtRequestFilters() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return((exchange,chain)->{

            if(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0) != null && exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).startsWith("Bearer "))
            {
                jwt = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring(7);

                username = jwtUtils.extractUserName(jwt);

                UserDetails user = service.loadUserByUsername(username);

                if(jwtUtils.validateToken(jwt,user) && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),jwtUtils.extract_roles(jwt));

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) exchange.getRequest()));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            return chain.filter(exchange);
        });
    }

    public static  class Config{

    }

}

