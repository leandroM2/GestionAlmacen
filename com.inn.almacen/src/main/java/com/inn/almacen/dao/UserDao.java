package com.inn.almacen.dao;

import com.inn.almacen.POJO.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailID(@Param("email") String email);
}
