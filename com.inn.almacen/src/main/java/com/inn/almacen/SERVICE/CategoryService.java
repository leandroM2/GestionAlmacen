package com.inn.almacen.SERVICE;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryService {

    ResponseEntity<String> NuevaCategoria(Map<String,String> requestMap);

}
