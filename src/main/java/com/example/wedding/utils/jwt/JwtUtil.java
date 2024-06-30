package com.example.wedding.utils.jwt;


import com.example.wedding.entities.User;
import com.example.wedding.exceptions.ErrorCode;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JwtUtil {
    public static final Map<String, Object> RS256_TOKEN_HEADER =
            Map.ofEntries(Map.entry("typ", "JWT"), Map.entry("alg", "RS256"));
    private static final String RSA_ALGORITHM = "RSA";
    private static final long JWT_EXPIRATION = 604800000L;

    @NonNull
    public static PublicKey extractRsaPublicKey(@NonNull final String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String extractedPublicKey = extractPublicKey(publicKey);
        final X509EncodedKeySpec encodedKeySpec =
                new X509EncodedKeySpec(Base64.getDecoder().decode(extractedPublicKey));
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(encodedKeySpec);
    }

    @NonNull
    public static PrivateKey extractRsaPrivateKey(@NonNull final String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String extractedPrivateKey = extractPrivateKey(privateKey);
        final PKCS8EncodedKeySpec encodedKeySpec =
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(extractedPrivateKey));
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(encodedKeySpec);
    }

    @NonNull
    private static String extractPublicKey(@NonNull final String publicKey) {
        return publicKey
                .replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
    }

    @NonNull
    private static String extractPrivateKey(@NonNull final String privateKey) {
        return privateKey
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
    }

    @NonNull
    public static Claims extractAllClaims(
            @NonNull final String jwt, @NonNull final PublicKey publickey) {
        final JwtParser jwtParser;
        try {
            jwtParser =
                    Jwts.parserBuilder()
                            .setSigningKeyResolver(
                                    new SigningKeyResolverAdapter() {
                                        @Override
                                        public Key resolveSigningKey(
                                                final JwsHeader header, final Claims claims) {
                                            return publickey;
                                        }
                                    })
                            .build();
        } catch (JwtException | UnsupportedOperationException | IllegalArgumentException e) {
            throw new JwtError(ErrorCode.AUTHENTICATION_ERROR);
        }

        try {
            return jwtParser.parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new JwtError(ErrorCode.AUTHENTICATION_ERROR);
        }
    }

    @NonNull
    public static String generateAccessToken(
            final User user,
            final PrivateKey privateKey
    ) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        Map<String, String> claims = new HashMap<>();

        claims.put("id", String.valueOf(user.getId()));
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().toString());

        return Jwts.builder()
                .setHeader(RS256_TOKEN_HEADER)
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
