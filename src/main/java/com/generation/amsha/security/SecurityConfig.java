package com.generation.amsha.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint, CustomUserDetailsService userDetailsService) {
        this.authEntryPoint = authEntryPoint;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors().and()  // Enable CORS in the security filter chain
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui**",
                                "/swagger-ui/**",
                                "/api/transaction/ipn",
                                "/api/transaction/**",
                                "/api/transactions/**",
                                "/api/budget/**",
                                "/api/blog/**",
                                "/api/user**",
                                "/api/user/**",
                                "/api/savings**",
                                "/api/savings/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    private static final String[] AUTH_WHITELIST = {
            "/api/auth/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}