package com.inn.almacen.dao;

import com.inn.almacen.POJO.Product;
import com.inn.almacen.WRAPPER.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    Product getById(@Param("prodId") String prodId);
    ProductView getByIdView(@Param("prodId") String prodId);
    List<ProductView> getAllProduct();
    Integer getCountCorr(@Param("prodId") String prodId);
}