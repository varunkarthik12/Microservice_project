package com.example.Authenticationservices.Config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private jwtRequestFilter jwtRequestFilter;




//    @Bean
//    public static PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }

//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws
//            Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder1);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((authorize) -> {authorize.requestMatchers("api/auth").
                        permitAll();

                }).sessionManagement((session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }


}
