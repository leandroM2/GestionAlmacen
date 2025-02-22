package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Client;
import com.inn.almacen.POJO.ClientDetail;
import com.inn.almacen.SERVICE.ClientDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.ClientDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.ClientDetailDao;
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
public class ClientDetailServiceImpl implements ClientDetailService {

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    ClientDetailDao clientDetailDao;

    @Override
    public ResponseEntity<String> addNewClientDetail(Map<String, String> requestMap) {
        log.info("Entra a add new clientDetail");
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateClientDetailMap(requestMap, false)){
                    clientDetailDao.save(getClientDetailFromMap(requestMap,false));
                    return AlmacenUtils.getResponseEntity("Nueva fábrica de cliente registrada exitosamente", HttpStatus.OK);
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
    public ResponseEntity<List<ClientDetailWrapper>> getAllClientDetail() {
        log.info("Dentro de Get All ClientDetail");
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                return new ResponseEntity<>(clientDetailDao.getAllClientDetail(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateClientDetail(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateClientDetailMap(requestMap, true)){
                    Optional optional=clientDetailDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        clientDetailDao.save(getClientDetailFromMap(requestMap,true));
                        return AlmacenUtils.getResponseEntity("Fábrica de cliente actualizada correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de fábrica no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteClientDetail(Integer id) {
        log.info("Dentro de delete ClientDetail");
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=clientDetailDao.findById(id);
                if(!optional.isEmpty()){
                    clientDetailDao.save(stateById(id));
                    return AlmacenUtils.getResponseEntity("Estado de fábrica de cliente actualizado correctamente.", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de fábrica no existe.", HttpStatus.OK);

                }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getById(Integer id) {
        log.info("Dentro de get clientDetail by id");
        try{
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=clientDetailDao.findById(id);
                if(!optional.isEmpty()){
                    List<ClientDetailWrapper> cdw=new ArrayList<>();
                    cdw.add(clientDetailDao.getViewById(id));
                    return new ResponseEntity<>(cdw,HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
                }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
                }
            }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getByFk(Integer client_fk, Integer cliDetId) {
        log.info("Dentro de get clientDetail by FK");
        try{
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<ClientDetailWrapper> cdw=clientDetailDao.getViewByCliDetId(client_fk,cliDetId);
                if(cdw.size()==1) return new ResponseEntity<>(cdw, HttpStatus.OK);
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getAllByFk(Integer client_fk) {
        log.info("Dentro de get all clientDetail by FK");
        try{
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<ClientDetailWrapper> cdw=clientDetailDao.getAllByClient(client_fk);
                if(cdw.size()>0) return new ResponseEntity<>(cdw, HttpStatus.OK);
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateClientDetailMap(Map<String, String> requestMap, boolean validateId){
        if( requestMap.containsKey("cliDetFactory") && requestMap.containsKey("cliDetAddress")
        && requestMap.containsKey("cliDetNumber") && requestMap.containsKey("client_fk")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private ClientDetail getClientDetailFromMap(Map<String, String> requestMap, boolean isUpd){
        ClientDetail clientDetail=new ClientDetail();

        Client client=new Client();
        client.setId(Integer.valueOf(requestMap.get("client_fk")));
        clientDetail.setClient(client);

        if(isUpd) clientDetail=clientDetailDao.getById(Integer.parseInt(requestMap.get("id")));

        clientDetail.setCliDetFactory(requestMap.get("cliDetFactory"));
        clientDetail.setCliDetAddress(requestMap.get("cliDetAddress"));
        clientDetail.setCliDetNumber(requestMap.get("cliDetNumber"));
        if(!isUpd){
            clientDetail.setCliDetState(true);
            clientDetail.setCliDetId(getTotalDetails(Integer.valueOf(requestMap.get("client_fk"))));
        }
        return clientDetail;
    }

    private Integer getTotalDetails(Integer client_fk){
        List<ClientDetailWrapper> cd=clientDetailDao.getAllByClient(client_fk);
        return cd.size()+1;
    }

    private ClientDetail stateById(Integer id){
        ClientDetail clientDetail=clientDetailDao.getById(id);
        clientDetail.setCliDetState(!clientDetail.getCliDetState());
        return clientDetail;
    }
}