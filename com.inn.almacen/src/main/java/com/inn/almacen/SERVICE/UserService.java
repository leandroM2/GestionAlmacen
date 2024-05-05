package com.inn.almacen.SERVICE;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

    ResponseEntity<String> Registrarse(Map<String,String> requestMap);
}
