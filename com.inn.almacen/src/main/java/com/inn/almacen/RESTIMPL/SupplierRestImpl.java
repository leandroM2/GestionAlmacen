package com.inn.almacen.RESTIMPL;

import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.REST.SupplierRest;
import com.inn.almacen.SERVICE.SupplierService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SupplierRestImpl implements SupplierRest {

    @Autowired
    SupplierService supplierService;

    @Override
    public ResponseEntity<String> addNewSupplier(Map<String, String> requestMap) {
        try {
            return supplierService.addNewSupplier(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Supplier>> getAllSupplier(String filterValue) {
        try {
            return supplierService.getAllSupplier(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateSupplier(Map<String, String> requestMap) {
        try {
            return supplierService.updateSupplier(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteSupplier(Integer id) {
        try {
            return supplierService.deleteSupplier(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Supplier>> getById(Integer id) {
        try {
            return supplierService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Supplier>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}