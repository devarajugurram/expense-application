package com.exp.backend.config;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.template.helpers.HelperComponentForToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Component
@WebFilter
public class JWTFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final HelperComponentForToken helperComponentForToken;
    private final UserDetail userDetail;

    /**
     *
     * @param helperComponentForToken
     * @param userDetail
     */
    public JWTFilter(HelperComponentForToken helperComponentForToken,UserDetail userDetail) {
        this.helperComponentForToken = helperComponentForToken;
        this.userDetail = userDetail;
    }

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilterInternal(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain filterChain) throws ServletException, IOException {

        List<String> urlList = List.of("/api/v1/register","/api/v1/otp/verify","/api/v1/login","/api/v1/refresh");
        if(urlList.contains(request.getServletPath())) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("""
                    {
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "Token expired or invalid"
                    }
                    """);
            return;
        }

        String accessToken = token.substring(7);
        UserDetails userDetails = userDetail.loadUserByUsername(helperComponentForToken.extractUserName(accessToken));
        if(!helperComponentForToken.validateToken(accessToken,userDetails)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("""
                    {
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "Token expired or invalid"
                    }
                    """);
            return;
        }
        if(SecurityContextHolder.getContext().getAuthentication() == null) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    /**
     *
     *
     * @param request
     * @return
     */
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/otp/verify")
                || path.equals("/api/v1/register")
                || path.equals("/api/v1/login");
    }
}
