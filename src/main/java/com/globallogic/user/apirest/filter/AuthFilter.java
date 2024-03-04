package com.globallogic.user.apirest.filter;

import com.globallogic.user.application.ports.output.UserOutputPort;
import com.globallogic.user.infrastructure.utils.jwt.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class AuthFilter implements Filter {
    private final JwtUtil jwtUtil;
    private final UserOutputPort userOutputPort;
    private static final List<String> PUBLIC_URLS = List.of(
            "/api/v1/auth/register",
            "/doc/swagger-ui/",
            "/api-docs",
            "/api-docs/swagger-config",
            "/h2-console",
            "/h2-console/"
    );

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            String uri = req.getRequestURI();
            String contextPath = req.getContextPath();
            String relativeUri = uri.substring(contextPath.length());

            if (PUBLIC_URLS.stream().anyMatch(relativeUri::startsWith)) {
                chain.doFilter(request, response);
                return;
            }

            String token = getTokenFromRequest(req);
            if (Boolean.TRUE.equals(jwtUtil.isTokenExpired(token))) {
                handleErrorToken(resp, "Token expirado.");
                return;
            }

            String username = jwtUtil.getUsernameFromToken(token);
            if (username == null || !userOutputPort.existsByUser(username)) {
                handleErrorToken(resp, "Error validando el usuario.");
                return;
            }
            chain.doFilter(request, resp);
        } catch (Exception e) {
            handleErrorToken(resp, "JWT Exception: " + e.getMessage());
        }
    }


    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }

        return null;
    }

    private void handleErrorToken(HttpServletResponse response, String error) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"" + error + "\"}");
    }
}