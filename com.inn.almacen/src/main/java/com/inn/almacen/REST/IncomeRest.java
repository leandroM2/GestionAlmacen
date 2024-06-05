package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.IncomeWrapper;
import com.inn.almacen.WRAPPER.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/income")
public interface IncomeRest {


    @PostMapping(path = "/add")
    ResponseEntity<String> addNewIncome(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<IncomeWrapper>> getAllIncome();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateIncome(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteIncome(@PathVariable Integer id);

}
