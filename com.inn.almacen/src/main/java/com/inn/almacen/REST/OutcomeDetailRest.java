package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/outcomeDetail")
public interface OutcomeDetailRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewOutcomeDetail(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<OutcomeDetailWrapper>> getAllOutcomeDetail();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateOutcomeDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteOutcomeDetail(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<OutcomeDetailWrapper>> getById(@PathVariable Integer id);
}