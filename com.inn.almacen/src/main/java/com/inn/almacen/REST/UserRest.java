package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewUser(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateUser(@RequestBody(required = true)
                                      Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<UserWrapper>> getById(@PathVariable Integer id);

    @PostMapping(path = "/iniciarSesion")
    public ResponseEntity<String> iniciarSesion(@RequestBody (required = true) Map<String,String> requestMap);

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadSign(@RequestParam("file") MultipartFile sign);

    @PostMapping(path = "/delfile/{nombre}")
    public ResponseEntity<String> deleteFile(@PathVariable String nombre);

    @PostMapping(path = "/auth/{id}")
    ResponseEntity<String> authorizeUser(@PathVariable Integer id);

    @GetMapping(path = "/checktoken")
    ResponseEntity<String> checktoken();

    @PostMapping(path = "/cambiarContrasena")
    ResponseEntity<String> cambiarContrasena(@RequestBody Map<String, String> requestMap);

}
