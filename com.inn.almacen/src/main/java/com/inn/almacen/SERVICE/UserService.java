package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> addNewUser(Map<String,String> requestMap);

    ResponseEntity<String> iniciarSesion(Map<String,String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllUser();

}
