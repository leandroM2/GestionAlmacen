package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getAllProduct();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getById(Integer id);

    ResponseEntity<String> deleteProduct(Integer id);
}
