package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.KardexWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/kardex")
public interface KardexRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewKardexEntry(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<KardexWrapper>> getAllKardex();

    @GetMapping(path = "/getDate")
    ResponseEntity<List<KardexWrapper>> getKardexByDate();

    @GetMapping(path = "/get/{id}")
    ResponseEntity<List<KardexWrapper>> getById(@PathVariable String id);

    @GetMapping(path = "/get/in")
    ResponseEntity<List<KardexWrapper>> getAllKardexIncome();

    @GetMapping(path = "/get/out")
    ResponseEntity<List<KardexWrapper>> getAllKardexOutcome();

    @PostMapping(path = "/file/{kardexId}")
    ResponseEntity<String> generateFile(@PathVariable String kardexId);

    @PostMapping(path = "/auth/{kardexId}")
    ResponseEntity<String> authorizeKardex(@PathVariable String kardexId);
}
