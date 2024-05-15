package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Category;
import com.inn.almacen.POJO.Product;
import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.SERVICE.ProductService;

import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.ProductWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Producto correctamente ingresado.",HttpStatus.OK);
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
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, true)){
                    Optional<Product> optional=productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Product product= getProductFromMap(requestMap,true);
                        product.setEstado(optional.get().getEstado());
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
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional=productDao.findById(id);
                if(!optional.isEmpty()){
                    productDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Producto eliminado correctamente.", HttpStatus.OK );
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
        if(requestMap.containsKey("nombre")){
            if(requestMap.containsKey("id") && validarId){
                return true;
            }else if(!validarId){
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean esAdd) {
        Category category=new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Supplier supplier=new Supplier();
        supplier.setId(Integer.parseInt(requestMap.get("supplierId")));

        Product product=new Product();
        if(esAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            product.setEstado(true);
        }

        product.setCategory(category);
        product.setNombre(requestMap.get("nombre"));
        product.setPrecio(Float.parseFloat(requestMap.get("precio")));
        product.setStock(Integer.parseInt(requestMap.get("stock")));
        product.setDescripcion(requestMap.get("descripcion"));
        return product;
    }
}