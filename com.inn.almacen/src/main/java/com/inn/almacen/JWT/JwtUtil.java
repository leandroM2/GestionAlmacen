package com.inn.almacen.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String llave="_!nt3gr@d0rII_";

    public String extractUsuario(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiracion(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public <T>T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(llave).parseClaimsJws(token).getBody();
    }

    private  Boolean isTokenExpirado(String token){
        return extractExpiracion(token).before(new Date());
    }

    public String generarToken(String usuario, String rol){
        Map<String, Object> claims= new HashMap<>();
        claims.put("rol",rol);
        return crearToken(claims,usuario);

    }

    private String crearToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,llave).compact();
    }

    public Boolean validarToken(String token, UserDetails userDetails){
        final String usuario= extractUsuario(token);
        return  (usuario.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }

}
