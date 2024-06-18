package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.*;
import com.inn.almacen.SERVICE.OutcomeDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.OutcomeDetailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class OutcomeDetailServiceImpl implements OutcomeDetailService {

    @Autowired
    OutcomeDetailDao outcomeDetailDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewOutcomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateOutcomeDetailMap(requestMap, false)){
                    outcomeDetailDao.save(getOutcomeDetailFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Producto de salida correctamente registrado.", HttpStatus.OK);
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
    public ResponseEntity<List<OutcomeDetailWrapper>> getAllOutcomeDetail() {
        try {
            return new ResponseEntity<>(outcomeDetailDao.getAllOutcomeDetail(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateOutcomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateOutcomeDetailMap(requestMap, true)){
                    Optional<OutcomeDetail> optional=outcomeDetailDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        OutcomeDetail outcomeDetail= getOutcomeDetailFromMap(requestMap,true);
                        outcomeDetailDao.save(outcomeDetail);
                        return AlmacenUtils.getResponseEntity("Producto de salida actualizado exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de salida de producto no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteOutcomeDetail(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional=outcomeDetailDao.findById(id);
                if(!optional.isEmpty()){
                    outcomeDetailDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Producto de salida eliminado correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de salida de producto no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OutcomeDetailWrapper>> getById(Integer id) {
        log.info("Dentro de get Income Detail by id");
        try {
            if (jwtFilter.isAdmin()){
                Optional optional=outcomeDetailDao.findById(id);
                if(!optional.isEmpty()){
                    OutcomeDetail outcomeDetail=outcomeDetailDao.getById(id);
                    List<OutcomeDetailWrapper> myList = new ArrayList<>();
                    myList.add(new OutcomeDetailWrapper(outcomeDetail.getId(),outcomeDetail.getCantidad(),outcomeDetail.getOutcome().getId(),
                            outcomeDetail.getOutcome().getFecha(), outcomeDetail.getOutcome().getClient().getId(),
                            outcomeDetail.getOutcome().getClient().getRazonSocial(), outcomeDetail.getOutcome().getClient().getRuc(),
                            outcomeDetail.getOutcome().getClient().getCorreo(), outcomeDetail.getOutcome().getClient().getContacto(),
                            outcomeDetail.getOutcome().getClient().getDireccion(), outcomeDetail.getOutcome().getUser().getId(),
                            outcomeDetail.getOutcome().getUser().getNombre(), outcomeDetail.getOutcome().getUser().getEmail(),
                            outcomeDetail.getOutcome().getUser().getEstado(), outcomeDetail.getProduct().getId(),
                            outcomeDetail.getProduct().getNombre(), outcomeDetail.getProduct().getColor(), outcomeDetail.getProduct().getPrecio(),
                            outcomeDetail.getProduct().getStock(), outcomeDetail.getProduct().getEstado(), outcomeDetail.getProduct().getCategory().getId(),
                            outcomeDetail.getProduct().getCategory().getNombre(), outcomeDetail.getProduct().getSupplier().getId(),
                            outcomeDetail.getProduct().getSupplier().getRazonSocial(), outcomeDetail.getProduct().getSupplier().getRuc(),
                            outcomeDetail.getProduct().getSupplier().getContacto()));
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

    private boolean validateOutcomeDetailMap(Map<String, String> requestMap, boolean validateId){
        if(requestMap.containsKey("cantidad")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private OutcomeDetail getOutcomeDetailFromMap(Map<String, String> requestMap, boolean esAdd){
        Outcome outcome=new Outcome();
        outcome.setId(Integer.parseInt(requestMap.get("outcomeId")));

        Product product=new Product();
        product.setId(Integer.parseInt(requestMap.get("productId")));

        OutcomeDetail outcomeDetail=new OutcomeDetail();
        if(esAdd){
            outcomeDetail.setId(Integer.parseInt(requestMap.get("id")));
        }
        outcomeDetail.setOutcome(outcome);
        outcomeDetail.setProduct(product);
        Integer cant=Integer.parseInt(requestMap.get("cantidad"));
        outcomeDetail.setCantidad(cant);
        ProductUpdate(product.getId(), cant, outcomeDetail.getId(), esAdd);
        return outcomeDetail;
    }

    private void ProductUpdate(Integer id, Integer cant, Integer incomeId, boolean esAdd){
        log.info("Hemos llegado hasta actualizacion de stock producto.");
        String sql = "SELECT stock FROM product WHERE id = ?";
        Integer stock = jdbcTemplate.queryForObject(sql, new Integer[]{id}, Integer.class);
        if(esAdd){
            sql = "SELECT cantidad FROM outcome_detail WHERE id = ?";
            Integer oldCant = jdbcTemplate.queryForObject(sql, new Integer[]{incomeId}, Integer.class);
            if(cant!=oldCant){
                log.info("Estamos actualizando Outcome detail.");
                Integer total = (cant > oldCant) ? cant-oldCant : oldCant-cant;
                boolean oper = (cant > oldCant) ? true : false;

                if(oper){
                    stock=stock-total;
                }else{
                    stock=stock+total;
                }
                sql = "UPDATE product SET stock = ? WHERE id = ?";
                jdbcTemplate.update(sql, stock, id);
            }
            log.info("Cantidades no fueron modificadas por user.");
        }else{
            log.info("Estamos insertando Outcome detail.");
            stock=stock-cant;
            sql = "UPDATE product SET stock = ? WHERE id = ?";
            jdbcTemplate.update(sql, stock, id);
        }
    }
}