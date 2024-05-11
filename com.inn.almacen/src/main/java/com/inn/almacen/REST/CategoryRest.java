package com.inn.almacen.REST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/categoria")
public interface CategoryRest {

    @PostMapping(path = "/nueva")
    ResponseEntity<String> NuevaCategoria(@RequestBody(required = true)
                                          Map<String,String> requestMap);
}
