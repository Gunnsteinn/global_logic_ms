package com.globallogic.user.apirest.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.user.infrastructure.utils.jwt.JWTUtils;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${header.authorization.key}")
    private String headerAuthorizationKey;
    @Value("${token.bearer.prefix}")
    private String tokenBearerPrefix;
    private final JWTUtils jwtUtils;

    private Claims setSigningKey(HttpServletRequest request) {
        String jwtToken = request.
                getHeader(headerAuthorizationKey).
                replace(tokenBearerPrefix, "");

        return Jwts.parser()
                .verifyWith(jwtUtils.getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

    }

    private void setAuthentication(Claims claims) {

        List<String> authorities = (List<String>) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                        authorities.stream().map(SimpleGrantedAuthority::new).toList());

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean isJWTValid(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(headerAuthorizationKey);
        return authenticationHeader != null && authenticationHeader.startsWith(tokenBearerPrefix);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isJWTValid(request)) {
                Claims claims = setSigningKey(request);
                if (claims.get("authorities") != null) {
                    setAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            log.error(e.getMessage());

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", e.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        }
    }

}