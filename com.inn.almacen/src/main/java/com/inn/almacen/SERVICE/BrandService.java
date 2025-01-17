package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Brand;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BrandService {
    ResponseEntity<String> addNewBrand(Map<String,String> requestMap);

    ResponseEntity<List<Brand>> getAllBrand(String filterValue);

    ResponseEntity<String> updateBrand(Map<String, String> requestMap);

    ResponseEntity<String> deleteBrand(Integer brandId);

    ResponseEntity<List<Brand>> getById(Integer brandId);
}
