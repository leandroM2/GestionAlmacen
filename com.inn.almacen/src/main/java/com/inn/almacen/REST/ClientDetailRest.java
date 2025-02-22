package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.ClientDetailWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/clientDetail")
public interface ClientDetailRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewClientDetail(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<ClientDetailWrapper>> getAllClientDetail();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateClientDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteClientDetail(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<ClientDetailWrapper>> getById(@PathVariable Integer id);

    @PostMapping(path = "/get/{client_fk}/{cliDetId}")
    ResponseEntity<List<ClientDetailWrapper>> getByFk(@PathVariable Integer client_fk, @PathVariable Integer cliDetId);

    @PostMapping(path = "/get/c/{client_fk}")
    ResponseEntity<List<ClientDetailWrapper>> getAllByFk(@PathVariable Integer client_fk);
}
