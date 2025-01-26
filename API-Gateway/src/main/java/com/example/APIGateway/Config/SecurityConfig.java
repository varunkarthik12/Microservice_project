package com.example.APIGateway.Config;

import com.example.APIGateway.Filters.JwtRequestFilters;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService userDetailsService;
//

    @Autowired
   private JwtRequestFilters jwtRequestFilter;




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
                .authorizeRequests((authorize) -> {authorize.anyRequest().
                        permitAll();

                }).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }


}
