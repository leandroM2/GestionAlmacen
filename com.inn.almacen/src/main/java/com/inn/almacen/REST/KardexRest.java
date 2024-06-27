package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.KardexWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/kardex")
public interface KardexRest {

    @GetMapping(path = "/get")
    ResponseEntity<List<KardexWrapper>> getAllKardex();
}
