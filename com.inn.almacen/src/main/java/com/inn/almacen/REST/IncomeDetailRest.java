package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.IncomeDetailWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/incomeDetail")
public interface IncomeDetailRest {


    @PostMapping(path = "/add")
    ResponseEntity<String> addNewIncomeDetail(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<IncomeDetailWrapper>> getAllIncomeDetail();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateIncomeDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteIncomeDetail(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<IncomeDetailWrapper>> getById(@PathVariable Integer id);

}
