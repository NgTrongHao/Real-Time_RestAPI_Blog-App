package com.nguyentronghao.realtime_restapi_blogapp.service.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt-expiration-milliseconds}")
    private long jwtExpiration;
    
    //generate JWT token
    public String generateToken(Authentication authentication) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiration);
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(key())
                .compact();
    }
    
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    
    //get username from JWT token
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    //validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException malformedJwtException) {
            throw new BadCredentialsException("Invalid token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BadCredentialsException("Expired token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BadCredentialsException("Unsupported token");
        }
    }
}
