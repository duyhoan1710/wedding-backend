package com.example.wedding.securities.filter;

import com.example.wedding.exceptions.ErrorCode;
import com.example.wedding.repositories.UserRepository;
import com.example.wedding.utils.jwt.JwtError;
import com.example.wedding.utils.jwt.JwtPayload;
import com.example.wedding.utils.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

import static com.example.wedding.securities.config.SpringSecurityConfig.WHITE_LIST_URL;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final PublicKey publicKey;
    private final UserRepository userRepository;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (this.isPublicRoute(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !isBearerToken(authHeader)) {
            this.authenticationEntryPoint.commence(request, response, new JwtError(ErrorCode.AUTHENTICATION_NOT_FOUND));
            return;
        }

        final String jwtToken = this.extractJwtFromAuthorizationHeader(authHeader);
        final Authentication authentication;

        try {
            authentication = this.getAuthenticationByJwt(jwtToken, this.publicKey);
        } catch (AuthenticationException error) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, new JwtError(ErrorCode.AUTHENTICATION_ERROR));
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @NonNull
    private Authentication getAuthenticationByJwt(
            @NonNull final String jwtToken, @NonNull final PublicKey publicKey) {
        final Claims claims = JwtUtil.extractAllClaims(jwtToken, publicKey);
        final JwtPayload jwtPayload = JwtPayload.from(claims);
        final String email = jwtPayload.getEmail();
        final String role = jwtPayload.getRole();
        final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new UsernamePasswordAuthenticationToken(email, jwtPayload, authorities);
    }

    private boolean isPublicRoute(final HttpServletRequest request) {
        String requestUrl = request.getRequestURI();

        String result = Arrays.stream(WHITE_LIST_URL).filter(url -> requestUrl.startsWith(url.replace("**", ""))).findFirst().orElse(null);

        return result != null;
    }

    private boolean isBearerToken(final String authHeader) {
        return StringUtils.isNotEmpty(authHeader)
                && StringUtils.startsWithIgnoreCase(authHeader, BEARER_PREFIX);
    }

    private String extractJwtFromAuthorizationHeader(final String authorizationHeader) {
        return authorizationHeader.substring(BEARER_PREFIX.length()).trim();
    }
}
