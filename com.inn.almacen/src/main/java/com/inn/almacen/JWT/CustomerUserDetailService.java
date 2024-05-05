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
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private com.inn.almacen.POJO.User userDetalle;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Dentro de método loadUserByUsername {}",email);

        userDetalle = userDao.findByEmailID(email);
        if(!Objects.isNull(userDetalle))
            return new User(userDetalle.getEmail(),userDetalle.getContrasena(), new ArrayList<>());
        else
            throw new UsernameNotFoundException("Usuario no encontrado.");
    }

    public com.inn.almacen.POJO.User getUserDetalle(){
        return userDetalle;
    }
}
