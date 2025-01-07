package com.spellingbee.spellingbee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final int EXPIRATION_IN_MINS = 15;

    private String key;

    public JwtService() {
        try {
            System.out.println("TEST: Bean is created.");
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey newKey = keyGen.generateKey();
            key = Base64.getEncoder().encodeToString(newKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String playerName) {
        System.out.println("TEST: generateToken method begins.");
        Map<String, Object> claims = new HashMap<>();
        String e = Jwts.builder()
                .claims()
                .add(claims)
                .subject(playerName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * EXPIRATION_IN_MINS)))
                .and()
                .signWith(getKey())
                .compact();
        System.out.println("TEST: generateToken method ends.");
        return e;
    }


    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        System.out.println("TEST: Key successfully generated: " + Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
