package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.UserRest;
import com.inn.almacen.SERVICE.UserService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.UserWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> addNewUser(Map<String, String> requestMap) {
        try {
            return userService.addNewUser(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> iniciarSesion(Map<String, String> requestMap) {
        try {
            return userService.iniciarSesion(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> uploadSign(MultipartFile sign) {
        try {
            return userService.uploadSign(sign);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteFile(String nombre) {
        try {
            return  userService.deleteFile(nombre);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> authorizeUser(Integer id) {
        try {
            return  userService.authorizeUser(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checktoken() {
        try {
            return userService.checktoken();
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> cambiarContrasena(Map<String, String> requestMap) {
        try {
            return userService.cambiarContrasena(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userService.getAllUser();

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            return userService.updateUser(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            return  userService.deleteUser(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getById(Integer id) {
        try {
            return userService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}