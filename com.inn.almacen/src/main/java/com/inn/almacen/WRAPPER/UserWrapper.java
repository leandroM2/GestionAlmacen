package com.inn.almacen.WRAPPER;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;

    private String nombre;

    private String email;

    private String estado;

    public UserWrapper(Integer id, String nombre, String email, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.estado = estado;
    }
}
