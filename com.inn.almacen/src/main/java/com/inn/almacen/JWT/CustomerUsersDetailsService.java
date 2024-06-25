package com.inn.almacen.JWT;

import com.inn.almacen.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    Jasypt jasypt;

    private com.inn.almacen.POJO.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Dentro de método loadUserByUsername {}",username);

        userDetail = userDao.findByEmailId(username);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getEmail(), jasypt.decrypting(userDetail.getContrasena()), new ArrayList<>());

        }else{
            throw new UsernameNotFoundException("Usuario no encontrado.");

        }
    }

    public com.inn.almacen.POJO.User getUserDetail(){
        return userDetail;
    }
}