package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.CustomerUsersDetailsService;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.JWT.JwtUtil;
import com.inn.almacen.POJO.User;
import com.inn.almacen.SERVICE.UserService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.UserWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewUser(Map<String, String> requestMap) {
        log.info("Dentro del registro {}", requestMap);
        try {
            if(validateAdd(requestMap)){
                User user=userDao.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("User registrado exitosamente.",HttpStatus.OK);
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

    @Override
    public ResponseEntity<String> iniciarSesion(Map<String, String> requestMap) {
        log.info("Dentro de inicio de sesión");
        try{
            Authentication auth= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("contrasena"))
            );
            if(auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetail().getEstado()==true){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRol()) + "\"}",
                    HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>
                            ("{\"mensaje\":\""+"Esperar la aprobación del administrador."+"\"}",
                                    HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            log.error("{}",e);
        }
        return new ResponseEntity<String>
                ("{\"mensaje\":\""+"Credenciales incorrectas."+"\"}",
                        HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        log.info("Dentro de get all user");
        try {
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);

            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        log.info("Dentro de update user");
        try {
            if(jwtFilter.isAdmin()){
                if(valdateUserMap(requestMap, true)){
                    Optional optional=userDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        userDao.save(getUserFromMap(requestMap, true));
                        return AlmacenUtils.getResponseEntity("Usuario actualizado correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de usuario no existe.", HttpStatus.OK);
                    }
                }
                return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean valdateUserMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("nombre") && requestMap.containsKey("email")
                && requestMap.containsKey("contrasena")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        log.info("Dentro de delete user");
        try {
            if (jwtFilter.isAdmin()){
                Optional optional=userDao.findById(id);
                if(!optional.isEmpty()){
                    User user=userDao.findByRol(id);
                    if(user.getRol().equalsIgnoreCase("user")){
                        userDao.deleteById(id);
                        return AlmacenUtils.getResponseEntity("Usuario eliminado correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("No tiene permisos suficientes para eliminar este registro.", HttpStatus.OK);
                    }
                }
                return AlmacenUtils.getResponseEntity("Id de usuario no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<User>> getById(Integer id) {
        log.info("Dentro de get user by id");
        try {
            if (jwtFilter.isAdmin()){
                Optional optional=userDao.findById(id);
                if(!optional.isEmpty()){
                    User user=userDao.findByRol(id);
                        List<User> myList = new ArrayList<User>();
                        myList.add(userDao.getById(id));
                        return new ResponseEntity<>(myList,HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateAdd(Map<String,String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("email")
                && requestMap.containsKey("contrasena")){
            return true;
        }else{
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap, boolean esAdd){
        User user= new User();
        if(esAdd){
            user.setId(Integer.parseInt(requestMap.get("id")));
        }
        user.setNombre(requestMap.get("nombre"));
        user.setEmail(requestMap.get("email"));
        user.setContrasena(requestMap.get("contrasena"));
        user.setEstado(false);
        user.setRol("user");
        return user;
    }
}