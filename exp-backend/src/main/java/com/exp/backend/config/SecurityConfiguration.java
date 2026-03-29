package com.exp.backend.config;


import com.exp.backend.aop.MessageInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 *
 *
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${app.host.front}")
    private String FRONTEND_URL;

    private final JWTFilter jwtFilter;
    private final UserDetail userDetail;

    /**
     *
     *
     * @param jwtFilter
     * @param userDetail
     */
    public SecurityConfiguration(JWTFilter jwtFilter,UserDetail userDetail) {
        this.jwtFilter = jwtFilter;
        this.userDetail = userDetail;
    }

    /**
     *
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .httpBasic(http -> http.disable())
                .csrf(c -> c.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/api/v1/register",
                                "/api/v1/otp/verify",
                                "/api/v1/login",
                                "/api/v1/refresh")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    /**
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource()  {

        CorsConfiguration co = new CorsConfiguration();
        co.setAllowedHeaders(List.of("*"));
        co.setAllowedMethods(List.of("PUT","GET","POST","PATCH","OPTIONS"));
        co.setAllowedOrigins(List.of(FRONTEND_URL));
        co.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource cs = new UrlBasedCorsConfigurationSource();
        cs.registerCorsConfiguration("/**",co);
        return cs;
    }

    /**
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userDetail);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

}
