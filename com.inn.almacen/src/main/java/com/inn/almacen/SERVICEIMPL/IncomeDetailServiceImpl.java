package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Income;
import com.inn.almacen.POJO.IncomeDetail;
import com.inn.almacen.POJO.Product;
import com.inn.almacen.SERVICE.IncomeDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.IncomeDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.IncomeDetailDao;
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
public class IncomeDetailServiceImpl implements IncomeDetailService {

    @Autowired
    IncomeDetailDao incomeDetailDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewIncomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateIncomeDetailMap(requestMap, false)){
                    incomeDetailDao.save(getIncomeDetailFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Producto de ingreso correctamente registrado.", HttpStatus.OK);
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
    public ResponseEntity<List<IncomeDetailWrapper>> getAllIncomeDetail() {
        try {
            return new ResponseEntity<>(incomeDetailDao.getAllIncomeDetail(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateIncomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateIncomeDetailMap(requestMap, true)){
                    Optional<IncomeDetail> optional=incomeDetailDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        IncomeDetail incomeDetail= getIncomeDetailFromMap(requestMap,true);
                        incomeDetailDao.save(incomeDetail);
                        return AlmacenUtils.getResponseEntity("Producto de ingreso actualizado exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de ingreso de producto no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteIncomeDetail(Integer id) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDetailDao.findById(id);
                if(!optional.isEmpty()){
                    incomeDetailDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Producto de ingreso eliminado correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de ingreso de producto no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeDetailWrapper>> getById(Integer id) {
        log.info("Dentro de get Income Detail by id");
        IncomeDetail incomeDetail;
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDetailDao.findById(id);
                if(!optional.isEmpty()){
                    incomeDetail=incomeDetailDao.getById(id);
                    List<IncomeDetailWrapper> myList = new ArrayList<>();
                    myList.add(new IncomeDetailWrapper(incomeDetail.getId(),incomeDetail.getCantidad(),incomeDetail.getIncome().getId(),
                            incomeDetail.getIncome().getFecha(), incomeDetail.getIncome().getSupplier().getId(),
                            incomeDetail.getIncome().getSupplier().getRazonSocial(), incomeDetail.getIncome().getSupplier().getRuc(),
                            incomeDetail.getIncome().getSupplier().getContacto(), incomeDetail.getProduct().getId(),
                            incomeDetail.getProduct().getNombre(), incomeDetail.getProduct().getColor(), incomeDetail.getProduct().getPrecio(),
                            incomeDetail.getProduct().getStock(), incomeDetail.getProduct().getEstado(), incomeDetail.getProduct().getCategory().getId(),
                            incomeDetail.getProduct().getCategory().getNombre(), incomeDetail.getProduct().getSupplier().getId(),
                            incomeDetail.getProduct().getSupplier().getRazonSocial(), incomeDetail.getProduct().getSupplier().getRuc(),
                            incomeDetail.getProduct().getSupplier().getContacto()));
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

    private boolean validateIncomeDetailMap(Map<String, String> requestMap, boolean validateId){
        if(requestMap.containsKey("cantidad")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private IncomeDetail getIncomeDetailFromMap(Map<String, String> requestMap, boolean esAdd){
        Income income=new Income();
        income.setId(Integer.parseInt(requestMap.get("incomeId")));

        Product product=new Product();
        product.setId(Integer.parseInt(requestMap.get("productId")));

        IncomeDetail incomeDetail=new IncomeDetail();
        if(esAdd){
            incomeDetail.setId(Integer.parseInt(requestMap.get("id")));
        }
        incomeDetail.setIncome(income);
        incomeDetail.setProduct(product);
        Integer cant=Integer.parseInt(requestMap.get("cantidad"));
        incomeDetail.setCantidad(cant);
        ProductUpdate(product.getId(), cant, incomeDetail.getId(), esAdd);
        return incomeDetail;
    }

    private void ProductUpdate(Integer id, Integer cant, Integer incomeId, boolean esAdd){
        log.info("Hemos llegado hasta actualizacion de stock producto.");
        String sql = "SELECT stock FROM product WHERE id = ?";
        Integer stock = jdbcTemplate.queryForObject(sql, new Integer[]{id}, Integer.class);
        if(esAdd){
            sql = "SELECT cantidad FROM income_detail WHERE id = ?";
            Integer oldCant = jdbcTemplate.queryForObject(sql, new Integer[]{incomeId}, Integer.class);
            if(cant!=oldCant){
                log.info("Estamos actualizando Income detail.");
                Integer total = (cant > oldCant) ? cant-oldCant : oldCant-cant;
                boolean oper = (cant > oldCant) ? true : false;

                if(oper){
                    stock=stock+total;
                }else{
                    stock=stock-total;
                }
                sql = "UPDATE product SET stock = ? WHERE id = ?";
                jdbcTemplate.update(sql, stock, id);
            }
            log.info("Cantidades no fueron modificadas por user.");
        }else{
            log.info("Estamos insertando Income detail.");
            stock=stock+cant;
            sql = "UPDATE product SET stock = ? WHERE id = ?";
            jdbcTemplate.update(sql, stock, id);
        }
    }
}