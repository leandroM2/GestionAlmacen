package com.inn.almacen.RESTIMPL;

import com.inn.almacen.POJO.Type;
import com.inn.almacen.REST.TypeRest;
import com.inn.almacen.SERVICE.TypeService;
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
public class TypeRestImpl implements TypeRest {

    @Autowired
    TypeService typeService;

    @Override
    public ResponseEntity<String> addNewType(Map<String, String> requestMap) {
        try {
            return typeService.addNewType(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Type>> getAllType(String filterValue) {
        try {
            return typeService.getAllType(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateType(Map<String, String> requestMap) {
        try {
            return typeService.updateType(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteType(Integer typeId) {
        try {
            return typeService.deleteType(typeId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Type>> getById(Integer typeId) {
        try {
            return typeService.getById(typeId);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Type>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
