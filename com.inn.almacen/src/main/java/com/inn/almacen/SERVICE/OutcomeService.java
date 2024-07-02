package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.OutcomeWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OutcomeService {
    ResponseEntity<String> addNewOutcome(Map<String, String> requestMap);

    ResponseEntity<List<OutcomeWrapper>> getAllOutcome();

    ResponseEntity<String> updateOutcome(Map<String, String> requestMap);

    ResponseEntity<String> deleteOutcome(Integer id);

    ResponseEntity<List<OutcomeWrapper>> getById(Integer id);

    ResponseEntity<String> authorizeOutcome(Integer id);

    ResponseEntity<String> generateGuiaRemision(Integer id);
}