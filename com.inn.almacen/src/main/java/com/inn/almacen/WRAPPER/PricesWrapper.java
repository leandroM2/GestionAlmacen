package com.inn.almacen.WRAPPER;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PricesWrapper {
    private Integer prodId;

    private Float prodPrice;

    private Boolean pricesState;

    public PricesWrapper(Integer prodId, Float prodPrice, Boolean pricesState) {
        this.prodId = prodId;
        this.prodPrice = prodPrice;
        this.pricesState = pricesState;
    }
}
