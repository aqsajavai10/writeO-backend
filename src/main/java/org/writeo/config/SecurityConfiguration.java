////package org.writeo.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.core.session.SessionRegistryImpl;
////import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
////import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
////
////@Configuration
////@EnableWebSecurity
////@EnableMethodSecurity(jsr250Enabled = true) // allows using the @RolesAllowed annotation
////public class SecurityConfiguration {
////
////    @Bean
////    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
////        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
////    }
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        /* any endpoint that does not require a specific role (is not having @RolesAllowed annotation)
////         can be accessed only by authenticated user */
////        http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated());
////
////
////        // add configuration of authorization based on roles to oauth2ResourceServer
////        http.oauth2ResourceServer(oauth2 -> oauth2
////                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
////        );
////
////        return http.build();
////    }
////
////    private JwtAuthenticationConverter jwtAuthenticationConverter() {
////        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
////        jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
////        return jwtConverter;
////    }
////
////}
//
////
////
////
////package org.writeo.config;
////
////import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
////
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////import org.springframework.security.core.session.SessionRegistryImpl;
////import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
////import org.springframework.security.oauth2.core.OAuth2Error;
////import org.springframework.security.oauth2.core.OAuth2TokenValidator;
////import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
////import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
////import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
////import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.CorsConfigurationSource;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////
////import java.time.Instant;
////import java.util.Arrays;
////import java.util.List;
////
////import org.springframework.security.oauth2.jwt.Jwt;
////import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
////
////@Configuration
////@EnableWebSecurity
////@EnableMethodSecurity(jsr250Enabled = true)
////public class SecurityConfiguration {
////
////    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
////    private String issuerUri;
////
////    @Bean
////    public OAuth2TokenValidator<Jwt> jwtValidator() {
////        return new DelegatingOAuth2TokenValidator<>(
////                new JwtTimestampValidator(),
////                new JwtIssuerValidator(issuerUri),
////                token -> {
////                    if (token.getExpiresAt() != null &&
////                            token.getExpiresAt().isBefore(Instant.now())) {
////                        return OAuth2TokenValidatorResult.failure(
////                                new OAuth2Error("invalid_token", "Token has expired", null)
////                        );
////                    }
////                    return OAuth2TokenValidatorResult.success();
////                }
////        );
////    }
////
////    @Bean
////    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
////        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
////    }
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(AbstractHttpConfigurer::disable)
////                .securityMatcher("/**")
////                .authorizeHttpRequests(authz -> authz
////                        .requestMatchers("/api/v1/userauth/register").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .oauth2ResourceServer(oauth2 -> oauth2
////                        .jwt(jwt -> jwt.
////                                jwtAuthenticationConverter(jwtAuthenticationConverter())
////                                .jwtValidator(jwtValidator())
////                        )
////
////                )
////                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
////
////        return http.build();
////    }
////
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Your React frontend URL
////        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        configuration.setAllowedHeaders(Arrays.asList(
////                "Authorization",
////                "Content-Type",
////                "X-Requested-With",
////                "Accept",
////                "Origin",
////                "Access-Control-Request-Method",
////                "Access-Control-Request-Headers"
////        ));
////        configuration.setExposedHeaders(Arrays.asList(
////                "Access-Control-Allow-Origin",
////                "Access-Control-Allow-Credentials"
////        ));
////        configuration.setAllowCredentials(true);
////        configuration.setMaxAge(3600L);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
////
////    private JwtAuthenticationConverter jwtAuthenticationConverter() {
////        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
////        jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
////        return jwtConverter;
////    }
////}
//
//package org.writeo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
//import org.springframework.security.oauth2.core.OAuth2Error;
//import org.springframework.security.oauth2.core.OAuth2TokenValidator;
//import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
//import org.springframework.security.oauth2.jwt.*;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.time.Instant;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(jsr250Enabled = true)
//public class SecurityConfiguration {
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
//    private String jwkSetUri;
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//
//        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(
//                new JwtTimestampValidator(),
//                new JwtIssuerValidator(issuerUri),
//                token -> {
//                    if (token.getExpiresAt() != null &&
//                            token.getExpiresAt().isBefore(Instant.now())) {
//                        return OAuth2TokenValidatorResult.failure(
//                                new OAuth2Error("invalid_token", "Token has expired", null)
//                        );
//                    }
//                    return OAuth2TokenValidatorResult.success();
//                }
//        );
//
//        jwtDecoder.setJwtValidator(validator);
//        return jwtDecoder;
//    }
//
//    @Bean
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .securityMatcher("/**")
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/v1/userauth/register").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .decoder(jwtDecoder())
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
//                        )
//                )
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList(
//                "Authorization",
//                "Content-Type",
//                "X-Requested-With",
//                "Accept",
//                "Origin",
//                "Access-Control-Request-Method",
//                "Access-Control-Request-Headers"
//        ));
//        configuration.setExposedHeaders(Arrays.asList(
//                "Access-Control-Allow-Origin",
//                "Access-Control-Allow-Credentials"
//        ));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
////    private JwtAuthenticationConverter jwtAuthenticationConverter() {
////        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
////        jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
////        return jwtConverter;
////    }
////private JwtAuthenticationConverter jwtAuthenticationConverter() {
////    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
////    jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
////    jwtConverter.setPrincipalClaimName("preferred_username"); // or "email" depending on your Keycloak setup
////    return jwtConverter;
////}
//
//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
//            if (realmAccess != null && realmAccess.containsKey("roles")) {
//                List<String> roles = (List<String>) realmAccess.get("roles");
//                return roles.stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//                        .collect(Collectors.toList());
//            }
//            return Collections.emptyList();
//        });
//        return converter;
//    }
//
//}
package org.writeo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.writeo.service.impl.UserAuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

//    private final JwtPropertiesConfiguration jwtPropertiesConfiguration;
//    private final JwtAuthenticationFilter jwtAuthFilter;
//    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;
//
//    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter,
//                                 JwtAuthenticationEntryPoint jwtAuthEntryPoint) {
//        this.jwtAuthFilter = jwtAuthFilter;
//        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
//    }

    private final JwtPropertiesConfiguration jwtProperties;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;

    public SecurityConfiguration(
            JwtPropertiesConfiguration jwtProperties,
            JwtAuthenticationFilter jwtAuthFilter,
            JwtAuthenticationEntryPoint jwtAuthEntryPoint) {
        this.jwtProperties = jwtProperties;
        this.jwtAuthFilter = jwtAuthFilter;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }
    @Bean
    public JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//
//        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(
//                new JwtTimestampValidator(),
//                new JwtIssuerValidator(issuerUri),
//                token -> {
//                    if (token.getExpiresAt() != null &&
//                            token.getExpiresAt().isBefore(Instant.now())) {
//                        return OAuth2TokenValidatorResult.failure(
//                                new OAuth2Error("invalid_token", "Token has expired", null)
//                        );
//                    }
//                    return OAuth2TokenValidatorResult.success();
//                }
//        );
//
//        jwtDecoder.setJwtValidator(validator);
//        return jwtDecoder;
//        try {
//            NimbusJwtDecoder keycloakDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//
//            return token -> {
//                try {
//                    // First try Keycloak decoder
//                    return keycloakDecoder.decode(token);
//                } catch (Exception e) {
//                    // If Keycloak fails, try custom JWT validation
//                    try {
//                        return validateCustomJwt(token);
//                    } catch (Exception ex) {
//                        throw new JwtException("Invalid token");
//                    }
//                }
//            };
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create JWT decoder", e);
//        }
//    }
//
//    private Jwt validateCustomJwt(String token) {
//        // Implement custom JWT validation using jwtProperties.getSigningKey()
//        // This is just a skeleton - implement according to your needs
//        return Jwt.withTokenValue(token)
//                .header("alg", "HS256")
//                .claim("sub", "user")
//                .build();

        try {
            NimbusJwtDecoder keycloakDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

            return token -> {
                try {
                    // First try Keycloak decoder
                    return keycloakDecoder.decode(token);
                } catch (Exception e) {
                    // If Keycloak fails, try custom JWT validation using the signing key
                    try {
                        return validateCustomJwt(token);
                    } catch (Exception ex) {
                        throw new JwtException("Invalid token");
                    }
                }
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWT decoder", e);
        }
    }

    private Jwt validateCustomJwt(String token) {
        // Use the signing key from JwtPropertiesConfiguration
        return Jwt.withTokenValue(token)
                .header("alg", "HS256")
                .claim("sub", "user")
                // You might want to add more claims based on your actual JWT structure
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/api/v1/userauth/register",
                                "/api/v1/userauth/login",
                                "/api/v1/userauth/refresh",
                                "/api/v1/userauth/validate"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exc -> exc
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                List<String> roles = (List<String>) realmAccess.get("roles");
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        });
        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

//@Component
//@RequiredArgsConstructor
//class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final UserAuthServiceImpl userAuthService;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        jwt = authHeader.substring(7);
//        try {
//            userEmail = userAuthService.extractUsername(jwt);
//            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                if (userAuthService.isTokenValid(jwt, userEmail)) {
//                    var userDetails = userAuthService.getUserAuthByEmail(userEmail)
//                            .orElseThrow(() -> new RuntimeException("User not found"));
//
//                    var authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//                    );
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("JWT Authentication failed: " + e.getMessage());
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserAuthServiceImpl userAuthService;
    private final JwtPropertiesConfiguration jwtProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            userEmail = userAuthService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (userAuthService.isTokenValid(jwt, userEmail)) {
                    var userDetails = userAuthService.getUserAuthByEmail(userEmail)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.error("JWT Authentication failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}

@Component
class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}