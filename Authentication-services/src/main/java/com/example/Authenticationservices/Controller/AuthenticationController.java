package com.example.Authenticationservices.Controller;

import com.example.Authenticationservices.Dto.AuthenticationRequestDto;
import com.example.Authenticationservices.Dto.AuthenticationResponseDto;
import com.example.Authenticationservices.Service.CustomUserDetailsService;
import com.example.Authenticationservices.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> createJwt(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws Exception
    {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(),authenticationRequestDto.getPassword()));
        }
        catch (BadCredentialsException e){
                throw new Exception("Incorrect User credentials",e);
    }

        UserDetails userDetails = service.loadUserByUsername(authenticationRequestDto.getUsername());

        String jwt = jwtUtils.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponseDto(jwt), HttpStatus.OK);
    }

}
