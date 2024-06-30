package com.inn.almacen.UTILS;

import com.inn.almacen.POJO.Archives;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AlmacenUtils {

    private AlmacenUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"mensaje\":\""+responseMessage+"\"}", httpStatus);

    }

    public static ResponseEntity<String> getResponseComplex(ResponseEntity<String> complex){
        return complex;
    }

    public static Boolean ArchiveIsEmpty(Archives arch){
        if(arch==null) return true;

        if (arch.getId()==null) return true; else return false;
    }


}
