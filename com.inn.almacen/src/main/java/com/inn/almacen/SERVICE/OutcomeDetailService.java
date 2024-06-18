package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OutcomeDetailService {
    ResponseEntity<String> addNewOutcomeDetail(Map<String, String> requestMap);

    ResponseEntity<List<OutcomeDetailWrapper>> getAllOutcomeDetail();

    ResponseEntity<String> updateOutcomeDetail(Map<String, String> requestMap);

    ResponseEntity<String> deleteOutcomeDetail(Integer id);

    ResponseEntity<List<OutcomeDetailWrapper>> getById(Integer id);
}