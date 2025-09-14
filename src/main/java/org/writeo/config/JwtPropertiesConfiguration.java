package org.writeo.config;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;

@Configuration
@Getter
public class JwtPropertiesConfiguration {

    @Value("${jwt.expiration:86400000}") // 24 hours default
    private long jwtExpiration;

    @Value("${jwt.refresh-token.expiration:604800000}") // 7 days default
    private long refreshExpiration;

    private final Key signingKey;

    public JwtPropertiesConfiguration() {
        this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public Key jwtSigningKey() {
        return this.signingKey;
    }
}