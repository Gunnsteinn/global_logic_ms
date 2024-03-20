package com.globallogic.user.apirest.filter;

import com.globallogic.user.infrastructure.utils.jwt.JWTUtils;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTAuthentication {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long tokenExpirationTime;
    @Value("${token.bearer.prefix}")
    private String tokenBearerPrefix;
    private final JWTUtils jwtUtils;

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .subject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(jwtUtils.getKey(), Jwts.SIG.HS256)
                .compact();
    }

}