package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.Jasypt;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.*;
import com.inn.almacen.SERVICE.IncomeDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.*;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class IncomeDetailServiceImpl implements IncomeDetailService {

    @Autowired
    IncomeDetailDao incomeDetailDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    Jasypt jasypt;

    @Autowired
    CategoryDao cd;

    @Autowired
    SupplierDao sd;

    @Autowired
    LocationDao ld;

    @Autowired
    TypeDao td;

    @Autowired
    PricesDao pd;

    @Autowired
    UserDao ud;

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
    //public ResponseEntity<List<IncomeDetailView>> getAllIncomeDetail() {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<IncomeDetailView> idv=incomeDetailDao.getAllIncomeDetail();
                return new ResponseEntity<>(incomeDetailBuilder(idv), HttpStatus.OK);
                //return new ResponseEntity<>(incomeDetailDao.getAllIncomeDetail(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateIncomeDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
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
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=incomeDetailDao.findById(id);
                IncomeDetail incomeDetail=incomeDetailDao.getById(id);
                if(!optional.isEmpty()){
                    String msg=restoreProduct(incomeDetail.getCantidad(), incomeDetail.getProduct().getProdId(), incomeDetail.getIncome().getId());
                    incomeDetailDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Ingreso de producto eliminado correctamente. "+msg, HttpStatus.OK );
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
                    myList.add(new IncomeDetailWrapper(incomeDetail.getId(),incomeDetail.getCantidad(),
                            incomeDetail.getPrecioVentaUnit(), incomeDetail.getOldPrecioVenta(), incomeDetail.getSaldo(), incomeDetail.getIncome().getId(),
                            incomeDetail.getIncome().getFecha(), incomeDetail.getIncome().getEstado(),
                            incomeDetail.getIncome().getUser().getId(), incomeDetail.getIncome().getUser().getNombre(),
                            incomeDetail.getIncome().getUserAuth().getId(), incomeDetail.getIncome().getUserAuth().getNombre(),
                            incomeDetail.getProduct().getProdId(), incomeDetail.getProduct().getProdDesc(), incomeDetail.getProduct().getProdCode(),
                            incomeDetail.getProduct().getProdStock(), incomeDetail.getProduct().getProdState(),
                            incomeDetail.getProduct().getCategory().getCatId(), incomeDetail.getProduct().getCategory().getCatName(),
                            incomeDetail.getProduct().getSupplier().getId(), incomeDetail.getProduct().getSupplier().getRazonSocial(),
                            incomeDetail.getProduct().getSupplier().getRuc(), incomeDetail.getProduct().getSupplier().getContacto(),
                            incomeDetail.getProduct().getType().getTypeId(), incomeDetail.getProduct().getType().getTypeName(),
                            incomeDetail.getProduct().getLocation().getLocationId(), incomeDetail.getProduct().getLocation().getLocationFloor()));
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
        if(requestMap.containsKey("cantidad") && requestMap.containsKey("precioVentaUnit")
            && requestMap.containsKey("incomeId") && requestMap.containsKey("productId")){
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

        Product product=productDao.getById(requestMap.get("productId"));

        IncomeDetail incomeDetail=new IncomeDetail();
        if(esAdd) incomeDetail.setId(Integer.parseInt(requestMap.get("id")));
        incomeDetail.setIncome(income);
        incomeDetail.setProduct(product);
        incomeDetail.setPrecioVentaUnit(Float.parseFloat(requestMap.get("precioVentaUnit")));
        incomeDetail.setOldPrecioVenta(Float.valueOf(jasypt.decrypting(product.getPrices().getProdPrice())));
        incomeDetail.setSaldo(product.getProdStock());
        Integer cant=Integer.parseInt(requestMap.get("cantidad"));
        incomeDetail.setCantidad(cant);
        Boolean state=validateState(Integer.parseInt(requestMap.get("incomeId")));
        if(state){
            Integer stock=updateProduct(product.getProdId(), cant, incomeDetail.getId(), esAdd);
            incomeDetail.setSaldo(stock);
        }
        return incomeDetail;
    }

    private Boolean validateState(Integer incomeId){
        Income income;
        income=incomeDao.getById(incomeId);
        Boolean state=income.getEstado();
        return state;
    }

    private Integer updateProduct(String id, Integer cant, Integer incomeId, boolean esAdd){
        log.info("Hemos llegado hasta actualizacion de stock producto.");
        String sql = "SELECT stock FROM product WHERE id = ?";
        Integer stock = jdbcTemplate.queryForObject(sql, new String[]{id}, Integer.class);
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
        return stock;
    }

    private String restoreProduct(Integer cant, String productId, Integer incomeId){
        log.info("Se retirará el stock actualizado en producto si registro fue autorizado");
        String msg;
        Income income=incomeDao.getById(incomeId);
        if(income.getEstado()){
            String sql = "SELECT stock from product WHERE id = ?";
            Integer stock = jdbcTemplate.queryForObject(sql, new String[]{productId}, Integer.class);
            stock=stock-cant;
            sql = "UPDATE product SET stock = ? WHERE id = ?";
            jdbcTemplate.update(sql, stock, productId);
            msg="El stock asignado por entrada ha sido retirado del producto";
        }else{
            msg="Stock de productos no fueron modificados debido a que el registro nunca fue autorizado";
        }
        return msg;
    }

    private List<IncomeDetailWrapper> incomeDetailBuilder(List<IncomeDetailView> idv){

        List<IncomeDetailWrapper> idw = new ArrayList<>();

        Iterator<IncomeDetailView> iterator = idv.iterator();
        while (iterator.hasNext()) {
            IncomeDetailView unit = iterator.next();
            Income i=incomeDao.getById(unit.getIncomeId());
            User u=ud.getById(i.getUser().getId());
            User uauth=ud.getById(i.getUserAuth().getId());
            Product prod=productDao.getById(unit.getProdId());
            Category c=cd.getById(prod.getCategory().getCatId());
            Supplier s=sd.getById(prod.getSupplier().getId());
            Type t=td.getById(prod.getType().getTypeId());
            Location l=ld.getById(prod.getLocation().getLocationId());
            Prices p=pd.getById(unit.getProdId());
            idw.add(new IncomeDetailWrapper
                    (unit.getId(), unit.getCantidad(), unit.getPrecioVentaUnit(), unit.getOldPrecioVenta(),
                    unit.getSaldo(), i.getId(), i.getFecha(), i.getEstado(), u.getId(), u.getNombre(),
                    uauth.getId(), uauth.getNombre(), prod.getProdId(), prod.getProdDesc(), prod.getProdCode(),
                    prod.getProdStock(), prod.getProdState(), c.getCatId(), c.getCatName(), s.getId(),
                    s.getRazonSocial(), s.getRuc(), s.getContacto(), t.getTypeId(), t.getTypeName(), l.getLocationId(),
                    l.getLocationFloor()));
        }
        return idw;
    }
}