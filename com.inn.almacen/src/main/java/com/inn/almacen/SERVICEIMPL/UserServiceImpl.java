package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.CustomerUsersDetailsService;
import com.inn.almacen.JWT.Jasypt;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
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

    @Autowired
    Jasypt jasypt;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResponseEntity<String> addNewUser(Map<String, String> requestMap) {
        log.info("Dentro del registro");
        try {
            if(jwtFilter.isSuperAdmin() || jwtFilter.isAdmin()){
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
            }
            return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
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
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("contrasena"))
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
    public ResponseEntity<String> uploadSign(MultipartFile sign) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if (sign.isEmpty()) return AlmacenUtils.getResponseEntity("EL ARCHIVO DE FIRMA SE ENCUENTRA VACÍO.", HttpStatus.BAD_REQUEST);
                if (!sign.getContentType().equals("image/png")) return AlmacenUtils.getResponseEntity("SOLO SE PERMITEN ARCHIVOS EN FORMATO PNG.", HttpStatus.BAD_REQUEST);

                String ruta=Paths.get("src", "main", "resources", "templates","auths") + File.separator;
                String fileName = renombrar(sign.getOriginalFilename());
                Path filePath = Paths.get(ruta, fileName);

                if (Files.exists(filePath) && !jwtFilter.isSuperAdmin()) return AlmacenUtils.getResponseEntity("USUARIO "+jwtFilter.getCurrentUser()+" NO CUENTA CON PERMISOS PARA ACTUALIZAR SU FIRMA. CONTACTAR AL ADMINISTRADOR.", HttpStatus.CONFLICT);
                Files.copy(sign.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return AlmacenUtils.getResponseEntity("ARCHIVO SUBIDO CORRECTAMENTE.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteFile(String nombre) {
        try {
            if(!jwtFilter.isSuperAdmin()) return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            nombre=nombre+".png";
            String ruta=Paths.get("src", "main", "resources", "templates","auths") + File.separator;
            Path filePath = Paths.get(ruta, nombre);
            if (Files.notExists(filePath)) return AlmacenUtils.getResponseEntity("FIRMA NO ENCONTRADA", HttpStatus.NOT_FOUND);
            Files.delete(filePath);
            return AlmacenUtils.getResponseEntity("FIRMA ELIMINADA CORRECTAMENTE: " +nombre, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> authorizeUser(Integer id) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                User myUser=userDao.findByEmailId(jwtFilter.getCurrentUser());
                User userUpd=userDao.findByRol(id);
                if(!validateStatus(myUser, userUpd)) return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
                updateState(id);
                return AlmacenUtils.getResponseEntity("Usuario "+userUpd.getNombre()+" ha sido autorizado.", HttpStatus.OK);
            }
            return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checktoken() {
        return AlmacenUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> cambiarContrasena(Map<String, String> requestMap) {
        try {
            User u=userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!u.equals("null")){
                if(jasypt.decrypting(u.getContrasena()).equals(requestMap.get("oldPassword"))){
                    String password=requestMap.get("newPassword");
                        password=jasypt.encrypting(password);
                        byte[] byteData = password.getBytes("UTF-8");
                        Blob blob=new SerialBlob(byteData);
                        u.setContrasena(blob);
                        userDao.save(u);
                    return AlmacenUtils.getResponseEntity("Contraseña actualizada correctamente.",HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Contraseña o correo incorrecto.", HttpStatus.BAD_REQUEST);
            }
            return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Boolean validateStatus(User myUser, User userUpd){
        Integer myRol=switchcase(myUser.getRol());
        Integer updRol=switchcase(userUpd.getRol());
        Boolean status=(myRol>updRol) ? true : false;
        return status;
    }

    public Integer switchcase(String rol){
        Integer val=0;
        switch (rol.toUpperCase()){
            case "USER":
                val=1;
                break;
            case "ADMIN":
                val=2;
                break;
            case "SUPERADMIN":
                val=3;
                break;
        }
            return val;
    }

    private void updateState(Integer userId){
        String sql = "UPDATE user SET estado = true WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    private void unauthUser(Integer userId){
        String sql = "UPDATE user SET estado = false WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        log.info("Dentro de get all user");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
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
            if(jwtFilter.isSuperAdmin() || jwtFilter.isAdmin()){
                if(validateUserMap(requestMap, true)){
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

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        log.info("Dentro de delete user");
        try {
            if (jwtFilter.isSuperAdmin()){
                Optional optional=userDao.findById(id);
                if(!optional.isEmpty()){
                    User user=userDao.findByRol(id);
                    if(user.getRol().equalsIgnoreCase("user") || user.getRol().equalsIgnoreCase("admin")){
                        //userDao.deleteById(id);
                        unauthUser(id);
                        return AlmacenUtils.getResponseEntity("Usuario deshabilitado correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("No tiene permisos suficientes para eliminar este registro.", HttpStatus.OK);
                    }
                }
                return AlmacenUtils.getResponseEntity("Id de usuario no existe.", HttpStatus.OK);
            } else if (jwtFilter.isAdmin()) {
                Optional optional=userDao.findById(id);
                if(!optional.isEmpty()){
                    User user=userDao.findByRol(id);
                    if(user.getRol().equalsIgnoreCase("user")){
                        //userDao.deleteById(id);
                        unauthUser(id);
                        return AlmacenUtils.getResponseEntity("Usuario deshabilitado correctamente.", HttpStatus.OK);
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
    public ResponseEntity<List<UserWrapper>> getById(Integer id) {
        log.info("Dentro de get user by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=userDao.findById(id);
                if(!optional.isEmpty()){
                        User user=userDao.getById(id);
                        List<UserWrapper> myList = new ArrayList<>();
                        myList.add(new UserWrapper(user.getId(), user.getNombre(), user.getEmail(), user.getEstado(), user.getRol()));
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

    private String renombrar(String original) {
        String extension = original.substring(original.lastIndexOf('.'));
        User u=userDao.findByEmailId(jwtFilter.getCurrentUser());
        String nuevo = u.getNombre() + extension;
        return nuevo;
    }

    private boolean validateUserMap(Map<String, String> requestMap, boolean validateId) {
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

    private boolean validateAdd(Map<String,String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("email")
                && requestMap.containsKey("contrasena")){
            return true;
        }else{
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap, boolean esAdd) {
        String password;
        User user= new User();
        if(esAdd) user.setId(Integer.parseInt(requestMap.get("id")));
        user.setNombre(requestMap.get("nombre"));
        user.setEmail(requestMap.get("email"));
        password=requestMap.get("contrasena");
        user.setRol(requestMap.containsKey("rol") ? requestMap.get("rol") : "user");
        user.setEstado(requestMap.containsKey("estado") ? Boolean.parseBoolean(requestMap.get("estado")) : false);
        try{
            password=jasypt.encrypting(password);
            byte[] byteData = password.getBytes("UTF-8");
            Blob blob=new SerialBlob(byteData);
            user.setContrasena(blob);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}