package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.POJO.User;
import com.inn.almacen.SERVICE.UserService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> Registrarse(Map<String, String> requestMap) {
        log.info("Dentro del registro {}", requestMap);

        try {
            if(validarRegistrarse(requestMap)){
                User user=userDao.findByEmailID(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return AlmacenUtils.getResponseEntity("Registrado exitosamente.",HttpStatus.OK);
                }else{
                    return AlmacenUtils.getResponseEntity("Email ya existe.", HttpStatus.BAD_REQUEST);
                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validarRegistrarse(Map<String,String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("email")
                &&requestMap.containsKey("contrasena")){
            return true;
        }else{
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user= new User();
        user.setNombre(requestMap.get("nombre"));
        user.setEmail(requestMap.get("email"));
        user.setContrasena(requestMap.get("contrasena"));
        user.setEstado("false");
        user.setRol("usuario");
        return user;
    }
}
