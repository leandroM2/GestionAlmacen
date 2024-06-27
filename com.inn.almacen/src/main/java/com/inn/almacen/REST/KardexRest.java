package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.KardexWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/kardex")
public interface KardexRest {

    @GetMapping(path = "/get")
    ResponseEntity<List<KardexWrapper>> getAllKardex();

    @GetMapping(path = "/get/{id}")
    ResponseEntity<List<KardexWrapper>> getById(@PathVariable String id);

    @GetMapping(path = "/get/in")
    ResponseEntity<List<KardexWrapper>> getAllKardexIncome();

    @GetMapping(path = "/get/out")
    ResponseEntity<List<KardexWrapper>> getAllKardexOutcome();
}
