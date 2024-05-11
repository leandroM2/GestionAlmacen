package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/registrarse")
    public ResponseEntity<String> Registrarse(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/iniciarSesion")
    public ResponseEntity<String> IniciarSesion(@RequestBody (required = true) Map<String,String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();




}
