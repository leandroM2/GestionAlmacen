package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Client;
import com.inn.almacen.SERVICE.ClientService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.ClientDao;
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
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao clientDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewClient(Map<String, String> requestMap) {
        log.info("Dentro de add new client");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateClientMap(requestMap, false)){
                    clientDao.save(getClientFromMap(requestMap,false));
                    return AlmacenUtils.getResponseEntity("Nuevo cliente agregado exitosamente.", HttpStatus.OK);
                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<List<Client>> getAllClient(String filterValue) {
        log.info("Dentro de get all client");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Client>>(clientDao.getAllClient(), HttpStatus.OK);
            }
            return new ResponseEntity<>(clientDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Client>>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateClient(Map<String, String> requestMap) {
        log.info("Dentro de update client");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateClientMap(requestMap, true)){
                    Optional optional= clientDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        clientDao.save(getClientFromMap(requestMap, true));
                        return AlmacenUtils.getResponseEntity("PCliente actualizado correctamente.", HttpStatus.OK);
                    }else {
                        return AlmacenUtils.getResponseEntity("Id de cliente no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteClient(Integer id) {
        log.info("Dentro de delete client");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=clientDao.findById(id);
                if (!optional.isEmpty()){
                    clientDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Cliente eliminado correctamente.", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de cliente no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Client>> getById(Integer id) {
        log.info("Dentro de client supplier by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=clientDao.findById(id);
                if(!optional.isEmpty()){
                    List<Client> myList = new ArrayList<>();
                    myList.add(clientDao.getById(id));
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

    private boolean validateClientMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("razonSocial") && requestMap.containsKey("ruc") && requestMap.containsKey("contacto")
            && requestMap.containsKey("correo") && requestMap.containsKey("direccion")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Client getClientFromMap(Map<String, String> requestMap, boolean esAdd) {
        Client client=new Client();
        if(esAdd){
            client.setId(Integer.parseInt(requestMap.get("id")));
        }
        client.setRazonSocial(requestMap.get("razonSocial"));
        client.setRuc(Long.parseLong(requestMap.get("ruc")));
        client.setContacto(Integer.parseInt(requestMap.get("contacto")));
        client.setCorreo(requestMap.get("correo"));
        client.setDireccion(requestMap.get("direccion"));
        return client;
    }
}