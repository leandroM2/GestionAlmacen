package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="ClientDetail.getById",
        query = "select cd from ClientDetail cd where cd.id=:id")

@NamedQuery(name="ClientDetail.getViewById",
        query = "select new com.inn.almacen.WRAPPER.ClientDetailWrapper " +
        "(cd.id, cd.cliDetId, cd.cliDetFactory, cd.cliDetAddress, cd.cliDetNumber, cd.cliDetState, cd.client.id) " +
        "from ClientDetail cd where cd.id=:id")

@NamedQuery(name="ClientDetail.getAllClientDetail",
        query = "select new com.inn.almacen.WRAPPER.ClientDetailWrapper " +
        "(cd.id, cd.cliDetId, cd.cliDetFactory, cd.cliDetAddress, cd.cliDetNumber, cd.cliDetState, cd.client.id) " +
        "from ClientDetail cd")

@NamedQuery(name="ClientDetail.getViewByCliDetId",
        query = "select new com.inn.almacen.WRAPPER.ClientDetailWrapper "+
        "(cd.id, cd.cliDetId, cd.cliDetFactory, cd.cliDetAddress, cd.cliDetNumber, cd.cliDetState, cd.client.id) " +
        "from ClientDetail cd where cd.client.id=:client_fk and cd.cliDetId=:cliDetId")

//new com.inn.almacen.wrapper.ClientDetailWrapper (cd.id, cd.cliDetId, cd.cliDetFactory, cd.cliDetAddress, cd.cliDetNumber, cd.cliDetState, cd.client.id)

@NamedQuery(name="ClientDetail.getAllByClient",
query = "select new com.inn.almacen.WRAPPER.ClientDetailWrapper " +
        "(cd.id, cd.cliDetId, cd.cliDetFactory, cd.cliDetAddress, cd.cliDetNumber, cd.cliDetState, cd.client.id) " +
        "from ClientDetail cd where cd.client.id=:client_fk")


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="clientDetail")
public class ClientDetail implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="cliDetId")
    private Integer cliDetId;

    @Column(name="cliDetFactory")
    private String cliDetFactory;

    @Column(name="cliDetAddress")
    private String cliDetAddress;

    @Column(name="cliDetNumber")
    private String cliDetNumber;

    @Column(name = "cliDetState")
    private Boolean cliDetState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_fk", nullable = false)
    private Client client;
}
