package com.example.wedding.utils.jwt;


import io.jsonwebtoken.Claims;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class JwtPayload {
    String iss; String sub; long iat; long exp; String email; String role; long id;

    @NonNull
    public static JwtPayload from(@NonNull final Claims claims) {
        JwtPayload jwtPayload = new JwtPayload();

        jwtPayload.setIss(claims.getIssuer());
        jwtPayload.setExp(claims.getExpiration().getTime());
        jwtPayload.setIat(claims.getIssuedAt().getTime());
        jwtPayload.setSub(claims.getSubject());
        jwtPayload.setEmail(claims.get("email", String.class));
        jwtPayload.setRole(claims.get("role", String.class));
        jwtPayload.setId(Long.parseLong(claims.get("id", String.class)));

        return jwtPayload;
    }
}
