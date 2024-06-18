package com.inn.almacen.REST;

import com.inn.almacen.POJO.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/client")
public interface ClientRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewClient(@RequestBody(required = true)
                                          Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Client>> getAllClient(@RequestParam(required = false)
                                                  String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateClient(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteClient(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<Client>> getById(@PathVariable Integer id);
}
