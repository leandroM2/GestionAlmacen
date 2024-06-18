package com.inn.almacen.REST;

import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.WRAPPER.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/supplier")
public interface SupplierRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewSupplier(@RequestBody(required = true)
                                          Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Supplier>> getAllSupplier(@RequestParam(required = false)
                                                  String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateSupplier(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteSupplier(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<Supplier>> getById(@PathVariable Integer id);

}