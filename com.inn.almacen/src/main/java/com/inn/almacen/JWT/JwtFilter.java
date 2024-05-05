package com.inn.almacen.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    Claims claims=null;
    private String usuario=null;

    @Autowired
    private CustomerUserDetailService service;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().matches("/user/login|/user/olvidoContrasena|/user/registrarse")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            String autorizacionHeader= httpServletRequest.getHeader("Autorizacion");
            String token=null;

            if(autorizacionHeader!=null && autorizacionHeader.startsWith("Bearer ")){
                token=autorizacionHeader.substring(7);
                usuario=jwtUtil.extractUsuario(token);
            }
        }

    }
}
