package com.inn.almacen.RESTIMPL;

import com.inn.almacen.POJO.Brand;
import com.inn.almacen.REST.BrandRest;
import com.inn.almacen.SERVICE.BrandService;
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
public class BrandRestImpl implements BrandRest {

    @Autowired
    BrandService brandService;

    @Override
    public ResponseEntity<String> addNewBrand(Map<String, String> requestMap) {
        try {
            return brandService.addNewBrand(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Brand>> getAllBrand(String filterValue) {
        try {
            return brandService.getAllBrand(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBrand(Map<String, String> requestMap) {
        try {
            return brandService.updateBrand(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBrand(Integer brandId) {
        try {
            return brandService.deleteBrand(brandId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Brand>> getById(Integer brandId) {
        try {
            return brandService.getById(brandId);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Brand>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
