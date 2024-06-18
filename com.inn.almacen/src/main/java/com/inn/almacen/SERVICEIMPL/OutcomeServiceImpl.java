package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Outcome;
import com.inn.almacen.POJO.Client;
import com.inn.almacen.POJO.User;
import com.inn.almacen.SERVICE.OutcomeService;

import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.OutcomeWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.OutcomeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    OutcomeDao outcomeDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewOutcome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateOutcomeMap(requestMap, false)){
                    outcomeDao.save(getOutcomeFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Salida correctamente registrado.", HttpStatus.OK);
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
    public ResponseEntity<List<OutcomeWrapper>> getAllOutcome() {
        try {
            return new ResponseEntity<>(outcomeDao.getAllOutcome(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateOutcome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateOutcomeMap(requestMap, true)){
                    Optional<Outcome> optional=outcomeDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Outcome outcome= getOutcomeFromMap(requestMap,true);
                        outcomeDao.save(outcome);
                        return AlmacenUtils.getResponseEntity("Salida actualizada exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de salida no existe.", HttpStatus.OK);
                    }
                }else{
                    return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> deleteOutcome(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional=outcomeDao.findById(id);
                if(!optional.isEmpty()){
                    outcomeDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Salida eliminada correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de salida no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OutcomeWrapper>> getById(Integer id) {
        log.info("Dentro de get Outcome by id");
        try {
            if (jwtFilter.isAdmin()){
                Optional optional=outcomeDao.findById(id);
                if(!optional.isEmpty()){
                    Outcome outcome=outcomeDao.getById(id);
                    List<OutcomeWrapper> myList = new ArrayList<>();
                    myList.add(new OutcomeWrapper(outcome.getId(), outcome.getFecha(), outcome.getClient().getId(),
                            outcome.getClient().getRazonSocial(), outcome.getClient().getRuc(), outcome.getClient().getCorreo(),
                            outcome.getClient().getContacto(), outcome.getClient().getDireccion(), outcome.getUser().getId(),
                            outcome.getUser().getNombre(), outcome.getUser().getEmail(), outcome.getUser().getEstado()));
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

    private boolean validateOutcomeMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("fecha")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Outcome getOutcomeFromMap(Map<String, String> requestMap, boolean esAdd) {
        Client client=new Client();
        client.setId(Integer.parseInt(requestMap.get("clientId")));
        User user=new User();
        user.setId(Integer.parseInt(requestMap.get("userId")));

        Outcome outcome=new Outcome();
        if(esAdd){
            outcome.setId(Integer.parseInt(requestMap.get("id")));
        }

        outcome.setClient(client);
        outcome.setUser(user);
        outcome.setFecha(Date.valueOf(requestMap.get("fecha")));
        return outcome;
    }
}
