package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Income;
import com.inn.almacen.POJO.IncomeDetail;
import com.inn.almacen.POJO.Product;
import com.inn.almacen.POJO.User;
import com.inn.almacen.SERVICE.IncomeService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.IncomeWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.IncomeDao;
import com.inn.almacen.dao.IncomeDetailDao;
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
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    IncomeDetailDao incomeDetailDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResponseEntity<String> addNewIncome(Map<String, String> requestMap) {
        log.info("Entra a add new income");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateIncomeMap(requestMap, false)){
                    incomeDao.save(getIncomeFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Solicitud de entrada iniciada. Inserte los productos.", HttpStatus.OK);
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
    public ResponseEntity<List<IncomeWrapper>> getAllIncome() {
        log.info("Dentro de Get All Income");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                return new ResponseEntity<>(incomeDao.getAllIncome(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateIncome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateIncomeMap(requestMap, true)){
                    Optional<Income> optional=incomeDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Income income= getIncomeFromMap(requestMap,true);
                        incomeDao.save(income);
                        return AlmacenUtils.getResponseEntity("Entrada actualizada exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de entrada no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteIncome(Integer id) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    incomeDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Entrada eliminada correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de entrada no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeWrapper>> getById(Integer id) {
        log.info("Dentro de get Income by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    Income income=incomeDao.getById(id);
                    List<IncomeWrapper> myList = new ArrayList<>();
                    myList.add(new IncomeWrapper(income.getId(), income.getFecha(), income.getEstado(),
                            income.getUser().getId(), income.getUser().getNombre(),
                            income.getUserAuth().getId(), income.getUserAuth().getNombre()));
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
    public ResponseEntity<String> authorizeIncome(Integer id) {
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    String user=jwtFilter.getCurrentUser();
                    updateState(user, id);
                    return AlmacenUtils.getResponseEntity("Entradas autorizadas por el supervisor "+user, HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de entrada no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateIncomeMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("fecha")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Income getIncomeFromMap(Map<String, String> requestMap, boolean esAdd) {
        Income income=new Income();
        User user=new User();
        User userAuth=new User();
        userAuth.setId(0);
        String name=jwtFilter.getCurrentUser();
        log.info("PRUEBA: "+name);
        String sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{name}, Integer.class);
        user.setId(userId);
        if(esAdd) income.setId(Integer.parseInt(requestMap.get("id")));
        income.setFecha(Date.valueOf(requestMap.get("fecha")));
        income.setEstado(requestMap.containsKey("estado") ? Boolean.parseBoolean(requestMap.get("estado")) : false);
        income.setUser(user);
        income.setUserAuth(userAuth);
        return income;
    }

    private void updateState(String user, Integer incomeId){
        log.info("Hemos llegado hasta actualizacion de estado de entrada");
        String sql;
        sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{user}, Integer.class);

        sql = "UPDATE income SET estado = true, autorizador_fk=? WHERE id = ?";
        jdbcTemplate.update(sql, userId, incomeId);

        sql = "SELECT id FROM income_detail WHERE income_fk=?";

        // Obtener una lista de Strings
        List<Integer> ids = jdbcTemplate.queryForList(sql, new Integer[]{incomeId}, Integer.class);

        log.info("Vamos a efectuar las actualizaciones de stock y precio en los productos");
        Integer i=0;
        while(i<ids.size()){;
            IncomeDetail incomeDetail=incomeDetailDao.getById(ids.get(i));
            updateProduct(incomeDetail.getProduct().getId(), incomeDetail.getId(), incomeDetail.getCantidad(), incomeDetail.getPrecioVentaUnit());
            i++;
        }
    }

    private void updateProduct(Integer productId, Integer incomeDetailId, Integer cant, Float precioVentaUnit){
        log.info("Hemos llegado hasta actualizacion de stock y precio producto.");
        String sql;
        Product product=productDao.getById(productId);
            log.info("Estamos insertando y precios a Income detail.");
            Integer stock=product.getStock();
            stock=stock+cant;
            Float precio=product.getPrecio();
            precio=Math.round(((precio+precioVentaUnit)/2) * 100.0f) / 100.0f;
            sql = "UPDATE product SET stock = ?, precio=? WHERE id = ?";
            jdbcTemplate.update(sql, stock, precio, productId);
            sql="UPDATE income_detail SET saldo = ? WHERE id = ?";
            jdbcTemplate.update(sql, stock, incomeDetailId);
        log.info("Valores de precio y stock de producto y detalle income actualizado");
    }

}