package com.inn.almacen.REST;

import com.inn.almacen.POJO.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/brand")
public interface BrandRest {


    @PostMapping(path = "/add")
    ResponseEntity<String> addNewBrand(@RequestBody(required = true)
                                          Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Brand>> getAllBrand(@RequestParam(required = false)
                                                  String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateBrand(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @PostMapping(path = "/delete/{brandId}")
    ResponseEntity<String> deleteBrand(@PathVariable Integer brandId);

    @PostMapping(path = "/get/{brandId}")
    ResponseEntity<List<Brand>> getById(@PathVariable Integer brandId);
}
