package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.SupplierDetailWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/supplierDetail")
public interface SupplierDetailRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewSupplierDetail(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<SupplierDetailWrapper>> getAllSupplierDetail();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateSupplierDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteSupplierDetail(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<SupplierDetailWrapper>> getViewById(@PathVariable Integer id);

    @PostMapping(path = "/get/{supplier_fk}/{supDetId}")
    ResponseEntity<List<SupplierDetailWrapper>> getViewBySupDetId(@PathVariable Integer supplier_fk, @PathVariable Integer supDetId);

    @PostMapping(path = "/get/s/{supplier_fk}")
    ResponseEntity<List<SupplierDetailWrapper>> getAllBySupplier(@PathVariable Integer supplier_fk);

}
