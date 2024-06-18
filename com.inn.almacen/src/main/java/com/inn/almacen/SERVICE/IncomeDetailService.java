package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.IncomeDetailWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IncomeDetailService {

    ResponseEntity<String> addNewIncomeDetail(Map<String, String> requestMap);

    ResponseEntity<List<IncomeDetailWrapper>> getAllIncomeDetail();

    ResponseEntity<String> updateIncomeDetail(Map<String, String> requestMap);

    ResponseEntity<String> deleteIncomeDetail(Integer id);

    ResponseEntity<List<IncomeDetailWrapper>> getById(Integer id);
}
