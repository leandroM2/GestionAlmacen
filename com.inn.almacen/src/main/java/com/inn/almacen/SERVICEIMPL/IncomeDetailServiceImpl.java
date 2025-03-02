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
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<IncomeDetailView> idv=incomeDetailDao.getAllIncomeDetail();
                return new ResponseEntity<>(incomeDetailBuilder(idv), HttpStatus.OK);
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
                            incomeDetail.getProduct().getSupplierDetail().getSupplier().getId(), incomeDetail.getProduct().getSupplierDetail().getSupplier().getRazonSocial(),
                            incomeDetail.getProduct().getSupplierDetail().getSupplier().getRuc(), incomeDetail.getProduct().getSupplierDetail().getSupplier().getContacto(),
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
            && requestMap.containsKey("incomeId") && requestMap.containsKey("prodId")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private IncomeDetail getIncomeDetailFromMap(Map<String, String> requestMap, boolean isUpd){
        Income income=new Income();
        income.setId(Integer.parseInt(requestMap.get("incomeId")));

        Product product=productDao.getById(requestMap.get("prodId"));

        IncomeDetail incomeDetail=new IncomeDetail();
        if(isUpd) incomeDetail.setId(Integer.parseInt(requestMap.get("id")));
        incomeDetail.setIncome(income);
        incomeDetail.setProduct(product);
        incomeDetail.setPrecioVentaUnit(Float.parseFloat(requestMap.get("precioVentaUnit")));
        incomeDetail.setOldPrecioVenta(Float.valueOf(jasypt.decrypting(product.getPrices().getProdPrice())));
        incomeDetail.setSaldo(product.getProdStock());
        Integer cant=Integer.parseInt(requestMap.get("cantidad"));
        incomeDetail.setCantidad(cant);
        Boolean state=validateState(Integer.parseInt(requestMap.get("incomeId")));
        if(state){
            Integer prodStock=updateProduct(product.getProdId(), cant, incomeDetail.getId(), isUpd);
            incomeDetail.setSaldo(prodStock);
        }
        return incomeDetail;
    }

    private Boolean validateState(Integer incomeId){
        Income income=incomeDao.getById(incomeId);
        return income.getEstado();
    }

    private Integer updateProduct(String prodId, Integer cant, Integer incomeDetailId, boolean isUpd){
        log.info("Hemos llegado hasta actualizacion de stock producto.");

        Product prod=productDao.getById(prodId);
        Integer prodStock=prod.getProdStock();

        if(isUpd){
            IncomeDetail incomeD=incomeDetailDao.getById(incomeDetailId);
            Integer oldCant=incomeD.getCantidad();

            if(cant!=oldCant){
                log.info("Estamos actualizando Income detail.");
                Integer total = (cant > oldCant) ? cant-oldCant : oldCant-cant;
                boolean op = (cant > oldCant) ? true : false;

                if(op){
                    prodStock=prodStock+total;
                }else{
                    prodStock=prodStock-total;
                }
                prod.setProdStock(prodStock);
                productDao.save(prod);
            }
            log.info("Cantidades no fueron modificadas por user.");
        }else{
            log.info("Estamos insertando Income detail.");
            prod.setProdStock(prodStock+cant);
            productDao.save(prod);
        }
        return prodStock;
    }

    private String restoreProduct(Integer cant, String prodId, Integer incomeId){
        log.info("Se retirará el stock actualizado en producto si registro fue autorizado");
        String msg;
        Income income=incomeDao.getById(incomeId);
        if(income.getEstado()){
            Product prod=productDao.getById(prodId);
            Integer stock=prod.getProdStock()-cant;
            prod.setProdStock(stock);
            productDao.save(prod);
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
            User uAuth=ud.getById(i.getUserAuth().getId());
            Product prod=productDao.getById(unit.getProdId());
            Category c=cd.getById(prod.getCategory().getCatId());
            Supplier s=sd.getById(prod.getSupplierDetail().getId());
            Type t=td.getById(prod.getType().getTypeId());
            Location l=ld.getById(prod.getLocation().getLocationId());
            Prices p=pd.getById(unit.getProdId());
            idw.add(new IncomeDetailWrapper
                    (unit.getId(), unit.getCantidad(), unit.getPrecioVentaUnit(), unit.getOldPrecioVenta(),
                    unit.getSaldo(), i.getId(), i.getFecha(), i.getEstado(), u.getId(), u.getNombre(),
                    uAuth.getId(), uAuth.getNombre(), prod.getProdId(), prod.getProdDesc(), prod.getProdCode(),
                    prod.getProdStock(), prod.getProdState(), c.getCatId(), c.getCatName(), s.getId(),
                    s.getRazonSocial(), s.getRuc(), s.getContacto(), t.getTypeId(), t.getTypeName(), l.getLocationId(),
                    l.getLocationFloor()));
        }
        return idw;
    }
}