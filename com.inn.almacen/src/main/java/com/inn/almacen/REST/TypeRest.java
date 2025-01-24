package com.inn.almacen.REST;

import com.inn.almacen.POJO.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/type")
public interface TypeRest {


    @PostMapping(path = "/add")
    ResponseEntity<String> addNewType(@RequestBody(required = true)
                                          Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Type>> getAllType(@RequestParam(required = false)
                                                  String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateType(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @PostMapping(path = "/delete/{typeId}")
    ResponseEntity<String> deleteType(@PathVariable Integer typeId);

    @PostMapping(path = "/get/{typeId}")
    ResponseEntity<List<Type>> getById(@PathVariable Integer typeId);
}
