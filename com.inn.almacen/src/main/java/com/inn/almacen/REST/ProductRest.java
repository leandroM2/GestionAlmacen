package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{prodId}")
    ResponseEntity<String> deleteProduct(@PathVariable String prodId);

    @PostMapping(path = "/get/{prodId}")
    ResponseEntity<List<ProductWrapper>> getById(@PathVariable String prodId);


}