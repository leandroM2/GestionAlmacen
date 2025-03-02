package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.Jasypt;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.*;
import com.inn.almacen.SERVICE.PricesService;
import com.inn.almacen.SERVICE.ProductService;

import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.ProductView;
import com.inn.almacen.WRAPPER.ProductWrapper;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    Jasypt jasypt;

    @Autowired
    PricesService pricesService;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    SupplierDao supplierDao;

    @Autowired
    SupplierDetailDao supplierDetailDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    TypeDao typeDao;

    @Autowired
    PricesDao pricesDao;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateProductMap(requestMap, false)){
                    if(validateCorr(requestMap)){
                        productDao.save(getProductFromMap(requestMap, false));
                        return AlmacenUtils.getResponseEntity("Producto correctamente ingresado.",HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity(AlmacenConstants.CORRELATIVO_DESBORDADO, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            List<ProductView> pv=productDao.getAllProduct();
            return new ResponseEntity<>(productBuilder(pv), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateProductMap(requestMap, true)){
                    Product pp=productDao.getById(requestMap.get("prodId"));
                    if(!pp.getProdId().isEmpty()){
                        Product product= getProductFromMap(requestMap,true);
                        product.setProdState(pp.getProdState());
                        productDao.save(product);
                        return AlmacenUtils.getResponseEntity("Producto actualizado exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de producto no existe.", HttpStatus.OK);
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
    public ResponseEntity<List<ProductWrapper>> getById(String prodId) {
        log.info("Dentro de get product by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Product pp=productDao.getById(prodId);
                log.info("PRUEBA: "+!pp.getProdId().isEmpty());
                if(!pp.getProdId().isEmpty()){
                    ProductView pv=productDao.getByIdView(prodId);
                    List<ProductView> listpv=new ArrayList<>();
                    listpv.add(pv);
                    return new ResponseEntity<>(productBuilder(listpv),HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(String prodId) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Product pp=productDao.getById(prodId);
                if(!pp.getProdId().isEmpty()){
                    productDao.save(stateById(prodId));
                    pricesService.deletePrices(prodId);
                    return AlmacenUtils.getResponseEntity("Producto desactivado correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de producto no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validarId) {
        if(requestMap.containsKey("prodDesc")){
            if(requestMap.containsKey("prodId") && validarId){
                return true;
            }else if(!validarId){
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isUpd) {
        Product product=new Product();

        Category category=new Category();
        category.setCatId(Integer.parseInt(requestMap.get("catId")));
        product.setCategory(category);

        SupplierDetail supplierDetail=supplierDetailDao.getById(Integer.parseInt(requestMap.get("supplierId")));
        product.setSupplierDetail(supplierDetail);
        Supplier s= supplierDao.getById(supplierDetail.getSupplier().getId());


        Type type =new Type();
        type.setTypeId(Integer.parseInt(requestMap.get("typeId")));
        product.setType(type);

        Location location=new Location();
        location.setLocationId(Integer.parseInt(requestMap.get("locationId")));
        product.setLocation(location);

        product.setProdDesc(requestMap.get("prodDesc"));
        product.setProdCode(requestMap.get("prodCode"));
        product.setProdStock(Integer.parseInt(requestMap.get("prodStock")));
        product.setProdState(true);

        Prices prices=new Prices();

        if(isUpd){
            product.setProdId(requestMap.get("prodId"));
            prices.setProdId(requestMap.get("prodId"));
            pricesService.updatePrices(requestMap);
            product.setPrices(prices);
            return product;
        }

        String prodId = (s.getId().toString().length()==1) ? "0"+s.getId().toString() : s.getId().toString();
        prodId=prodId.concat((requestMap.get("catId").length()==1) ? "0"+requestMap.get("catId") : requestMap.get("catId"));
        prodId=prodId.concat((requestMap.get("typeId").length()==1) ? "0"+requestMap.get("typeId") : requestMap.get("typeId"));
        Integer count=productDao.getCountCorr(prodId);
        String val=(String.valueOf(count+1));
        prodId=prodId.concat( val.length()==1 ? "0"+val : val);

        product.setProdId(prodId);

        requestMap.put("prodId",prodId);
        pricesService.addNewPrices(requestMap);

        prices.setProdId(requestMap.get("prodId"));
        product.setPrices(prices);

        return product;
    }

    private Boolean validateCorr(Map<String, String> requestMap){
        SupplierDetail supplierDetail=supplierDetailDao.getById(Integer.parseInt(requestMap.get("supplierId")));
        Supplier s= supplierDao.getById(supplierDetail.getSupplier().getId());

        String prodId = (s.getId().toString().length()==1) ? "0"+s.getId().toString() : s.getId().toString();
        prodId=prodId.concat((requestMap.get("catId").length()==1) ? "0"+requestMap.get("catId") : requestMap.get("catId"));
        prodId=prodId.concat((requestMap.get("typeId").length()==1) ? "0"+requestMap.get("typeId") : requestMap.get("typeId"));

        Integer count=productDao.getCountCorr(prodId);

        Boolean b=count<99 ? true : false;

        return b;
    }

    private Product stateById(String prodId) {
        Product product;
        product=productDao.getById(prodId);
        product.setProdState(!product.getProdState());
        return product;
    }

    private List<ProductWrapper> productBuilder(List<ProductView> pv){

        List<ProductWrapper> pw = new ArrayList<>();

        Iterator<ProductView> iterator = pv.iterator();
        while (iterator.hasNext()) {
            ProductView unit = iterator.next();
            Category c= categoryDao.getById(unit.getCatId());
            SupplierDetail sd= supplierDetailDao.getById(unit.getSupplierDetailId());
            Supplier s=supplierDao.getById(sd.getSupplier().getId());
            Type t= typeDao.getById(unit.getTypeId());
            Location l= locationDao.getById(unit.getLocationId());
            Prices p= pricesDao.getById(unit.getId());
            pw.add(new ProductWrapper(unit.getId(), unit.getProdDesc(), unit.getProdCode(),
                    unit.getProdStock(), unit.getProdState(), unit.getCatId(),
                    c.getCatName(), unit.getSupplierDetailId(), sd.getSupDetId(), sd.getSupDetFactory(),
                    sd.getSupDetAddress(), sd.getSupDetNumber(), s.getId(), s.getRazonSocial(), s.getRuc(),
                    s.getContacto(), unit.getTypeId(),
                    t.getTypeName(), unit.getLocationId(),
                    l.getLocationFloor(), p.getProdId(),
                    String.valueOf(jasypt.decrypting(p.getProdPrice()))));
        }
        return pw;
    }
}