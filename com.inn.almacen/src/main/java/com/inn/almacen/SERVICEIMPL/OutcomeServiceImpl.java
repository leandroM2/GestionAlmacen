package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.*;
import com.inn.almacen.SERVICE.OutcomeService;

import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.OutcomeWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.OutcomeDao;
import com.inn.almacen.dao.OutcomeDetailDao;
import com.inn.almacen.dao.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    OutcomeDetailDao outcomeDetailDao;

    @Autowired
    ProductDao productDao;

    @Override
    public ResponseEntity<String> addNewOutcome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
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
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){

                return new ResponseEntity<>(outcomeDao.getAllOutcome(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateOutcome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
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
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
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
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=outcomeDao.findById(id);
                if(!optional.isEmpty()){
                    Outcome outcome=outcomeDao.getById(id);
                    List<OutcomeWrapper> myList = new ArrayList<>();
                    myList.add(new OutcomeWrapper(outcome.getId(), outcome.getFecha(),outcome.getEstado() , outcome.getClient().getId(),
                            outcome.getClient().getRazonSocial(), outcome.getClient().getRuc(), outcome.getClient().getCorreo(),
                            outcome.getClient().getContacto(), outcome.getClient().getDireccion(), outcome.getUser().getId(),
                            outcome.getUser().getNombre(), outcome.getUserAuth().getId(), outcome.getUserAuth().getNombre()));
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
    @Override
    public ResponseEntity<String> authorizeOutcome(Integer id){
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=outcomeDao.findById(id);
                if(!optional.isEmpty()){
                    String user=jwtFilter.getCurrentUser();
                    updateState(user, id);
                    return AlmacenUtils.getResponseEntity("Salidas autorizadas por el supervisor "+user, HttpStatus.OK );
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

    private boolean validateOutcomeMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("fecha") && requestMap.containsKey("clientId")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Outcome getOutcomeFromMap(Map<String, String> requestMap, boolean esAdd) {
        Outcome outcome=new Outcome();
        Client client=new Client();
        User user=new User();
        User userAuth=new User();
        userAuth.setId(0);

        String name=jwtFilter.getCurrentUser();
        String sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{name}, Integer.class);
        user.setId(userId);
        if(esAdd) outcome.setId(Integer.parseInt(requestMap.get("id")));
        client.setId(Integer.parseInt(requestMap.get("clientId")));

        outcome.setFecha(Date.valueOf(requestMap.get("fecha")));
        outcome.setEstado(requestMap.containsKey("estado") ? Boolean.parseBoolean(requestMap.get("estado")) : false);
        outcome.setClient(client);
        outcome.setUser(user);
        outcome.setUserAuth(userAuth);

        return outcome;
    }

    private void updateState(String user, Integer outcomeId){
        log.info("Hemos llegado hasta actualizacion de estado de salida");
        String sql;
        sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{user}, Integer.class);

        sql = "UPDATE outcome SET estado = true, autorizador_fk=? WHERE id = ?";
        jdbcTemplate.update(sql, userId, outcomeId);

        sql = "SELECT id FROM outcome_detail WHERE outcome_fk=?";

        // Obtener una lista de Strings
        List<Integer> ids = jdbcTemplate.queryForList(sql, new Integer[]{outcomeId}, Integer.class);

        log.info("Vamos a efectuar las actualizaciones de monto y precio en los productos");
        Integer i=0;
        while(i<ids.size()){;
            OutcomeDetail outcomeDetail=outcomeDetailDao.getById(ids.get(i));
            updateProduct(outcomeDetail.getProduct().getId(), outcomeDetail.getCantidad());
            i++;
        }
    }

    private void updateProduct(Integer ProductId, Integer cant){
        log.info("Hemos llegado hasta actualizacion de stock y precio producto.");
        String sql;
        Product product=productDao.getById(ProductId);
        log.info("Estamos insertando y precios a Outcome detail.");
        Integer stock=product.getStock();
        stock=stock-cant;
        sql = "UPDATE product SET stock = ? WHERE id = ?";
        jdbcTemplate.update(sql, stock, ProductId);
        log.info("Valores de stock y precio de producto actualizado");
    }
}
