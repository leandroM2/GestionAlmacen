package com.inn.almacen.dao;

import com.inn.almacen.POJO.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeDao extends JpaRepository<Type, Integer>{

    Type getById(@Param("typeId") Integer typeId);

    List<Type> getAllType();
}
