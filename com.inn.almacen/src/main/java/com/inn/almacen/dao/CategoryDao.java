package com.inn.almacen.dao;

import com.inn.almacen.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    Category getById(@Param("id") Integer id);
    List<Category> getAllCategory();

}
