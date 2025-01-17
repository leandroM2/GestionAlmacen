package com.inn.almacen.dao;

import com.inn.almacen.POJO.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandDao extends JpaRepository<Brand, Integer>{

    Brand getById(@Param("brandId") Integer brandId);

    List<Brand> getAllBrand();
}
