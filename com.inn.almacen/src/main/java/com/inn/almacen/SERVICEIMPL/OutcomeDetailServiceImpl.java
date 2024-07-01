package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.*;
import com.inn.almacen.SERVICE.OutcomeDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
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
    OutcomeDao outcomeDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewOutcomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateOutcomeDetailMap(requestMap, false)){
                    Boolean bool=validateStock(Integer.parseInt(requestMap.get("productId")),
                            Integer.parseInt(requestMap.get("cantidad")));
                    if(bool){
                        outcomeDetailDao.save(getOutcomeDetailFromMap(requestMap, false));
                        return AlmacenUtils.getResponseEntity("Producto de salida correctamente registrado.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Advertencia: stock quedará por debajo de los mínimos recomendados (200 u).", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<List<OutcomeDetailWrapper>> getAllOutcomeDetail() {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                return new ResponseEntity<>(outcomeDetailDao.getAllOutcomeDetail(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateOutcomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
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
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=outcomeDetailDao.findById(id);
                OutcomeDetail outcomeDetail=outcomeDetailDao.getById(id);
                if(!optional.isEmpty()){
                    String msg=restoreProduct(outcomeDetail.getCantidad(), outcomeDetail.getProduct().getId(), outcomeDetail.getOutcome().getId());
                    outcomeDetailDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Salida de producto eliminado correctamente. "+msg, HttpStatus.OK );
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
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=outcomeDetailDao.findById(id);
                if(!optional.isEmpty()){
                    OutcomeDetail outcomeDetail=outcomeDetailDao.getById(id);
                    List<OutcomeDetailWrapper> myList = new ArrayList<>();
                    myList.add(new OutcomeDetailWrapper(outcomeDetail.getId(),outcomeDetail.getCantidad(), outcomeDetail.getPrecioDeVenta(), outcomeDetail.getSaldo(),
                            outcomeDetail.getOutcome().getId(), outcomeDetail.getOutcome().getFecha(), outcomeDetail.getOutcome().getEstado(),
                            outcomeDetail.getOutcome().getClient().getId(), outcomeDetail.getOutcome().getClient().getRazonSocial(),
                            outcomeDetail.getOutcome().getClient().getRuc(), outcomeDetail.getOutcome().getClient().getCorreo(),
                            outcomeDetail.getOutcome().getClient().getContacto(), outcomeDetail.getOutcome().getClient().getDireccion(),
                            outcomeDetail.getOutcome().getUser().getId(), outcomeDetail.getOutcome().getUser().getNombre(),
                            outcomeDetail.getOutcome().getUserAuth().getId(), outcomeDetail.getOutcome().getUserAuth().getNombre(), outcomeDetail.getProduct().getId(),
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
        if(requestMap.containsKey("cantidad") && requestMap.containsKey("outcomeId")
                && requestMap.containsKey("productId")){
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

        Product product=productDao.getById(Integer.parseInt(requestMap.get("productId")));

        OutcomeDetail outcomeDetail=new OutcomeDetail();
        if(esAdd) outcomeDetail.setId(Integer.parseInt(requestMap.get("id")));
        outcomeDetail.setOutcome(outcome);
        outcomeDetail.setProduct(product);
        Integer cant=Integer.parseInt(requestMap.get("cantidad"));
        outcomeDetail.setSaldo(product.getStock());
        outcomeDetail.setCantidad(cant);
        outcomeDetail.setPrecioDeVenta(product.getPrecio());
        Boolean state=validateState(Integer.parseInt(requestMap.get("outcomeId")));
        if(state){
            Integer stock=updateProduct(product.getId(), cant, outcomeDetail.getId(), esAdd);
            outcomeDetail.setSaldo(stock);
        }
        return outcomeDetail;
    }

    private Boolean validateState(Integer outcomeId){
        Outcome outcome;
        outcome=outcomeDao.getById(outcomeId);
        Boolean state=outcome.getEstado();
        return state;
    }

    private Integer updateProduct(Integer id, Integer cant, Integer incomeId, boolean esAdd){
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
        return stock;
    }

    private String restoreProduct(Integer cant, Integer productId, Integer outcomeId){
        log.info("Se retirará el stock actualizado en producto si registro fue autorizado");
        String msg;
        Outcome outcome=outcomeDao.getById(outcomeId);
        if(outcome.getEstado()){
            String sql = "SELECT stock from product WHERE id = ?";
            Integer stock = jdbcTemplate.queryForObject(sql, new Integer[]{productId}, Integer.class);
            stock=stock+cant;
            sql = "UPDATE product SET stock = ? WHERE id = ?";
            jdbcTemplate.update(sql, stock, productId);
            msg="El stock retirado ha sido reasignado al producto";
        }else{
            msg="Stock de productos no fueron modificados debido a que el registro nunca fue autorizado";
        }
        return msg;
    }

    private Boolean validateStock(Integer productId, Integer cant){
        Product product;
        product=productDao.getById(productId);
        Integer dif=product.getStock()-cant;
        Boolean bool=(dif>200) ? true : false;
        return bool;
    }
}