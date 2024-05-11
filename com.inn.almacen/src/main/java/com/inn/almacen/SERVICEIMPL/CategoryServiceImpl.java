package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Category;
import com.inn.almacen.SERVICE.CategoryService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> NuevaCategoria(Map<String, String> requestMap) {
        try {
            if(jwtFilter.esAdmin()){
                if(validateCategoryMap(requestMap,false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity
                            ("Nueva categoria agregada con exito", HttpStatus.OK);
                    
                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validarId) {
        if(requestMap.containsKey("nombre")){
            if (requestMap.containsKey("id") && validarId){
                return true;
            }else if(!validarId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String,String> requestMap, Boolean esAdd){
        Category category= new Category();
        if(esAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setNombre(requestMap.get("nombre"));
        return category;
    }

}