package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.SERVICE.UserService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<String> registrarse(Map<String, String> requestMap) {
        log.info("Dentro del registro {}", requestMap);
        if(validarRegistrarse(requestMap)){

        }else{
            return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
        }
        return null;

    }

    private boolean validarRegistrarse(Map<String,String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("email")
                &&requestMap.containsKey("contrasena")){
            return true;
        }else{
            return false;
        }
    }
}
