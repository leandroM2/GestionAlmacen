package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Location;
import com.inn.almacen.SERVICE.LocationService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.LocationDao;
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
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationDao locationDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewLocation(Map<String, String> requestMap) {
        log.info("Dentro de Add New Location");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateLocationMap(requestMap,false)){
                    locationDao.save(getLocationFromMap(requestMap,false));
                    return AlmacenUtils.getResponseEntity
                            ("Nuevo espacio de almacén agregado con exito.", HttpStatus.OK);
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
    public ResponseEntity<List<Location>> getAllLocation(String filterValue) {
        log.info("Dentro de Get All Location");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Location>>(locationDao.getAllLocation(),HttpStatus.OK);
            }
            return new ResponseEntity<>(locationDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Location>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateLocation(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateLocationMap(requestMap, true)){
                    Optional optional = locationDao.findById(Integer.parseInt(requestMap.get("locationId")));
                    if(!optional.isEmpty()){
                        locationDao.save(getLocationFromMap(requestMap, true));
                        return AlmacenUtils.getResponseEntity("Espacio de almacén actualizado correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de almacén no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteLocation(Integer locationId) {
        log.info("Dentro de delete Location");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=locationDao.findById(locationId);
                if(!optional.isEmpty()){
                    locationDao.save(stateById(locationId));
                    return AlmacenUtils.getResponseEntity("Espacio de almacén actualizado correctamente", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de almacén no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Location>> getById(Integer locationId) {
        log.info("Dentro de get Location by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=locationDao.findById(locationId);
                if(!optional.isEmpty()){
                    List<Location> myList = new ArrayList<>();
                    myList.add(locationDao.getById(locationId));
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

    private boolean validateLocationMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("locationFloor")){
            if(requestMap.containsKey("locationId") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Location getLocationFromMap(Map<String, String> requestMap, boolean isUpd) {
        Location location=new Location();
        if(isUpd){
            location.setLocationId(Integer.parseInt(requestMap.get("locationId")));
        }
        location.setLocationFloor(requestMap.get("locationFloor"));
        location.setLocationState(true);
        return location;
    }

    private Location stateById(Integer locationId) {
        Location location;
        location=locationDao.getById(locationId);
        location.setLocationState(!location.getLocationState());
        return location;
    }
}
