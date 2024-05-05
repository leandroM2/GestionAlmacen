package com.inn.almacen.SERVICE;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

    ResponseEntity<String> registrarse(Map<String,String> requestMap);
}
