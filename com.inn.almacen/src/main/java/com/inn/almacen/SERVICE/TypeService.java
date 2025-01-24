package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Type;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TypeService {
    ResponseEntity<String> addNewType(Map<String,String> requestMap);

    ResponseEntity<List<Type>> getAllType(String filterValue);

    ResponseEntity<String> updateType(Map<String, String> requestMap);

    ResponseEntity<String> deleteType(Integer typeId);

    ResponseEntity<List<Type>> getById(Integer typeId);
}
