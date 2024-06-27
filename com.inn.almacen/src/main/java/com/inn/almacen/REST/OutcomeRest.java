package com.inn.almacen.REST;

import com.inn.almacen.WRAPPER.OutcomeWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/outcome")
public interface OutcomeRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewOutcome(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<OutcomeWrapper>> getAllOutcome();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateOutcome(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteOutcome(@PathVariable Integer id);

    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<OutcomeWrapper>> getById(@PathVariable Integer id);

    @PostMapping(path = "/auth/{id}")
    ResponseEntity<String> authorizeOutcome(@PathVariable Integer id);
}