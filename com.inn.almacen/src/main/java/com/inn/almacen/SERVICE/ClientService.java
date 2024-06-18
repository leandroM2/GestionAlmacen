package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ClientService {

    ResponseEntity<String> addNewClient(Map<String, String> requestMap);

    ResponseEntity<List<Client>> getAllClient(String filterValue);

    ResponseEntity<String> updateClient(Map<String, String> requestMap);

    ResponseEntity<String> deleteClient(Integer id);

    ResponseEntity<List<Client>> getById(Integer id);


}
