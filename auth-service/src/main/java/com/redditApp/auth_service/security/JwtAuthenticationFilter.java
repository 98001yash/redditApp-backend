package com.redditApp.auth_service.security;

import com.redditApp.auth_service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Service
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        try {
            Claims claims = jwtService.extractAllClaims(token);

            if (claims.getExpiration() != null && claims.getExpiration().before(new java.util.Date())) {
                log.warn("JWT expired for token: {}", token);
                filterChain.doFilter(request, response);
                return;
            }

            Long userId;
            try {
                userId = claims.get("userId", Long.class);
                if (userId == null) {
                    userId = Long.valueOf(claims.getSubject());
                }
            } catch (Exception e) {
                userId = Long.valueOf(claims.getSubject());
            }

            UserDetails userDetails = userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("JWT authentication successful for userId: {}", userId);

        } catch (ExpiredJwtException eje) {
            log.warn("JWT token expired: {}", eje.getMessage());
        } catch (Exception ex) {
            log.warn("Failed to authenticate JWT: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger") ||
                path.startsWith("/actuator");
    }
}