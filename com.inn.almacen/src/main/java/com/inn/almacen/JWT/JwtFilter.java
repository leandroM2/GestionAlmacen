package com.inn.almacen.JWT;
import io.jsonwebtoken.Claims;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUsersDetailsService service;

    Claims claims=null;
    private String userName =null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().matches("/user/iniciarSesion|/user/olvidoContrasena|/user/registrarse")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            String authorizationHeader= httpServletRequest.getHeader("Autorizacion");
            String token=null;

            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                token=authorizationHeader.substring(7);
                userName =jwtUtil.extractUsername(token);
                claims=jwtUtil.extractAllClaims(token);
            }

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=service.loadUserByUsername(userName);
                if (jwtUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //distinto de video
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails((javax.servlet.http.HttpServletRequest) httpServletRequest)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }

        }
    }
    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("rol"));
    }

    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("rol"));
    }

    public String getCurrentUser(){
        return userName;
    }

}