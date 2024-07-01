package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Archives;
import com.inn.almacen.SERVICE.ArchivesService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.ArchivesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArchivesServicesImpl implements ArchivesService {

    @Autowired
    ArchivesDao archivesDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addArchive(Map<String, String> requestMap) {
        log.info("Entra a add new archive");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateIncomeMap(requestMap, false)){
                    archivesDao.save(getArchivesFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Ruta de archivo correctamente almacenada", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Resource> descargarPDF(String kardexId) {
        try {
            log.info("Estamos entrando a descargar pdf");
            Path rutaArchivo = Paths.get("data", "orders", kardexId + ".pdf");

            if (!Files.exists(rutaArchivo)){
                log.info("no encuentra la ruta");
                return ResponseEntity.notFound().build();
            }
            byte[] contenido = Files.readAllBytes(rutaArchivo);
            ByteArrayResource recurso = new ByteArrayResource(contenido);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + kardexId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(contenido.length)
                    .body(recurso);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Archives>> getAllArchives(String filterValue) {
        log.info("Dentro de Get All Archives");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<>(archivesDao.getAllArchives(), HttpStatus.OK);
            }
            return new ResponseEntity<>(archivesDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Archives>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Archives>> getById(String id) {
        log.info("Dentro de get archives by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<Archives> archive = new ArrayList<>();
                archive.add(archivesDao.getById(id));
                return new ResponseEntity<>(archive,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateIncomeMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("id") && requestMap.containsKey("ruta")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Archives getArchivesFromMap(Map<String, String> requestMap, boolean esAdd) {;
        Archives arch=new Archives();
        arch.setId(requestMap.get("id"));
        arch.setRuta(requestMap.get("ruta"));
        return arch;
    }

}