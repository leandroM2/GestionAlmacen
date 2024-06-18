package com.inn.almacen.WRAPPER;

import com.inn.almacen.POJO.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;

    private String nombre;

    private String email;

    private Boolean estado;

    private String rol;

    public UserWrapper(Integer id, String nombre, String email, Boolean estado, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.estado = estado;
        this.rol=rol;
    }
}
