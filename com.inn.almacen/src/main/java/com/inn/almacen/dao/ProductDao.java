package com.inn.almacen.dao;

import com.inn.almacen.POJO.Product;
import com.inn.almacen.WRAPPER.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();
}
