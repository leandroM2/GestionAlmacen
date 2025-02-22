package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class ClientDetailWrapper {

    private Integer id;
    private Integer cliDetId;
    private String cliDetFactory;
    private String cliDetAddress;
    private String cliDetNumber;
    private Boolean cliDetState;
    private Integer clientId;

    public ClientDetailWrapper(Integer id, Integer cliDetId, String cliDetFactory, String cliDetAddress,
                               String cliDetNumber, Boolean cliDetState, Integer clientId) {
        this.id = id;
        this.cliDetId = cliDetId;
        this.cliDetFactory = cliDetFactory;
        this.cliDetAddress = cliDetAddress;
        this.cliDetNumber = cliDetNumber;
        this.cliDetState = cliDetState;
        this.clientId = clientId;
    }
}