package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.UserRest;
import com.inn.almacen.SERVICE.UserService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> registrarse(Map<String, String> requestMap) {
        try {
            return userService.registrarse(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
