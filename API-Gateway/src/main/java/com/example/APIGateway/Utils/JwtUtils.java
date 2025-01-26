package com.example.APIGateway.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {


        @Autowired
        private ModelMapper modelMapper;
        private String SECRET_KEY = "varun";

        public String extractUserName(String token)
        {
            return extractClaim(token, Claims::getSubject);
        }

        public Date extractExpiration(String token)
        {
            return extractClaim(token,Claims::getExpiration);
        }

        public <T> T extractClaim(String token, Function<Claims,T> claimResolver)
        {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        }

        private Claims extractAllClaims (String token)
        {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }

        private Boolean isTokenExpired(String token)
        {
            return extractExpiration(token).before(new Date());
        }

        public List<GrantedAuthority> extract_roles(String token)
        {
            Claims claims = extractAllClaims(token);

            List<GrantedAuthority> authorities = new ArrayList<>();
            List<String> roles = (List<String>) claims.get("roles");
            authorities = roles.stream()
                    .map((role)->new SimpleGrantedAuthority(role)).collect(Collectors.toList());

            return authorities;

        }

        private static String toJsonArray(String[] roles) {
        StringBuilder jsonArray = new StringBuilder("[");
        for (int i = 0; i < roles.length; i++) {
            jsonArray.append(roles[i]);
            if (i < roles.length - 1) {
                jsonArray.append(",");
            }
        }
        jsonArray.append("]");
        return jsonArray.toString();
    }
        public String createToken(Map<String,Object>claims,String subject, String[]roles)
        {
            return Jwts.builder().setClaims(claims)
                    .claim("roles",toJsonArray(roles))
                    .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                    .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
        }

        public String generateToken(UserDetails userDetails)
        {

            String[] roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .toArray(String[]::new);

            Map<String,Object> claims =new HashMap<>();
            return createToken(claims,userDetails.getUsername(),roles);
        }

        public Boolean validateToken(String token,UserDetails userDetails)
        {
            final String username = extractUserName(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }





}
