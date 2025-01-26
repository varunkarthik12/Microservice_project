package com.example.Authenticationservices.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

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
