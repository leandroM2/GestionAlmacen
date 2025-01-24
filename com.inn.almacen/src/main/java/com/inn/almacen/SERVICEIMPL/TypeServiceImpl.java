package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Type;
import com.inn.almacen.SERVICE.TypeService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.TypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeDao typeDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewType(Map<String, String> requestMap) {
        log.info("Dentro de Add New Type");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateBrandMap(requestMap,false)){
                    typeDao.save(getBrandFromMap(requestMap,false));
                    return AlmacenUtils.getResponseEntity
                            ("Nueva tipo agregada con exito.", HttpStatus.OK);

                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Type>> getAllType(String filterValue) {
        log.info("Dentro de Get All Type");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Type>>(typeDao.getAllType(),HttpStatus.OK);
            }
            return new ResponseEntity<>(typeDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Type>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateType(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateBrandMap(requestMap, true)){
                    Optional optional = typeDao.findById(Integer.parseInt(requestMap.get("typeId")));
                    if(!optional.isEmpty()){
                        typeDao.save(getBrandFromMap(requestMap, true));
                        return AlmacenUtils.getResponseEntity("Tipo actualizada correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de tipo no existe.", HttpStatus.OK);
                    }
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
    public ResponseEntity<String> deleteType(Integer typeId) {
        log.info("Dentro de delete Type");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional= typeDao.findById(typeId);
                if(!optional.isEmpty()){
                    typeDao.save(stateById(typeId));
                    return AlmacenUtils.getResponseEntity("Estado de tipo actualizado correctamente", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de tipo no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Type>> getById(Integer typeId) {
        log.info("Dentro de get Type by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional= typeDao.findById(typeId);
                if(!optional.isEmpty()){
                    List<Type> myList = new ArrayList<>();
                    myList.add(typeDao.getById(typeId));
                    return new ResponseEntity<>(myList,HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateBrandMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("typeName")){
            if(requestMap.containsKey("typeId") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Type getBrandFromMap(Map<String, String> requestMap, boolean isUpd) {
        Type type =new Type();
        if(isUpd){
            type.setTypeId(Integer.parseInt(requestMap.get("typeId")));
        }
        type.setTypeName(requestMap.get("typeName").toUpperCase());
        type.setTypeState(true);
        return type;
    }


    private Type stateById(Integer typeId) {
        Type type;
        type = typeDao.getById(typeId);
        type.setTypeState(!type.getTypeState());
        return type;
    }
}
