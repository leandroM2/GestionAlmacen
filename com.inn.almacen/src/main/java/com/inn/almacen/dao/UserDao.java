package com.inn.almacen.dao;

import com.inn.almacen.POJO.User;
import com.inn.almacen.WRAPPER.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    User findByRol(@Param("id") Integer id);

    User getById(@Param("id") Integer id);

    List<UserWrapper> getAllUser();

    User findByEmail(String email);
}