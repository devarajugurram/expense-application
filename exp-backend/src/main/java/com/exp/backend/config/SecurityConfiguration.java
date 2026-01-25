package com.exp.backend.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


/**
 * ClassName - SecurityConfiguration
 * This class is used for Custom Bean creation and access from application context.
 * This class also used for CSRF allowed methods and uris.
 * It helps in CORS. which helps in permitting which client url.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1//otp/verify","/api/v1/register","/api/v1/login")
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basic -> basic.disable())
                .cors(h -> h.configurationSource(getConfiguration()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(
                    logout -> logout.logoutUrl("/api/v1/logout")
                            .clearAuthentication(true)
                            .logoutSuccessHandler(((request, response, authentication) -> {
                                Cookie[] cookies = request.getCookies();
                                if(cookies != null) {
                                    for(Cookie cookie : cookies) {
                                        Cookie expiredCookie = new Cookie(cookie.getName(),null);
                                        expiredCookie.setMaxAge(0);
                                        expiredCookie.setPath("/");
                                        response.addCookie(expiredCookie);
                                    }
                                }
                                SecurityContextHolder.clearContext();
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.setContentType("application/json");
                                response.getWriter().write("{\"message\": \"Logout successful\"}");
                            })))
                .build();
    }


    @Bean
    public CorsConfigurationSource getConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
        cors.setAllowCredentials(true);
        cors.setAllowedMethods(Arrays.asList("POST","GET","DELETE","PUT","PATCH","OPTIONS"));
        cors.setAllowedHeaders(Arrays.asList("*"));
        cors.setMaxAge(360L);
        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",cors);
        return url;
    }

    /**
     * MethodName - caffeineCache
     * This method used to create OTPCache and UserCache beans.
     * Caffeine is an in memory cache provider, here we register buckets of respective data types to be stored.
     * We mention Expiry time of data,Maximum Data is allowed.
     * @return CacheManager It helps to store temporary data in the form of buckets.
     */
    @Bean
    public CacheManager caffeineCache() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.registerCustomCache("otpCache", Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .maximumSize(10_000)
                        .build());

        manager.registerCustomCache("userCache", Caffeine.newBuilder()
                        .expireAfterWrite(30, TimeUnit.MINUTES)
                        .maximumSize(50_000)
                        .build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(myUserDetails);
        dao.setPasswordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }

}
