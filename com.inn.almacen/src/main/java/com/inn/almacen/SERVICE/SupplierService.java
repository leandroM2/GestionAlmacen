package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.WRAPPER.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    ResponseEntity<String> addNewSupplier(Map<String, String> requestMap);

    ResponseEntity<List<Supplier>> getAllSupplier(String filterValue);

    ResponseEntity<String> updateSupplier(Map<String, String> requestMap);

    ResponseEntity<String> deleteSupplier(Integer id);

    ResponseEntity<List<Supplier>> getById(Integer id);

}