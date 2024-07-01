package com.inn.almacen.REST;

import com.inn.almacen.POJO.Archives;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/archives")
public interface ArchivesRest {

    @PostMapping("/add")
    ResponseEntity<String> addArchives(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Archives>> getAllArchives(@RequestParam(required = false)
                                                  String filterValue);
    @PostMapping(path = "/get/{id}")
    ResponseEntity<List<Archives>> getById(@PathVariable String id);

    @GetMapping("/descargar/{kardexId}")
    ResponseEntity<Resource> descargarPDF(@PathVariable String kardexId);
}
