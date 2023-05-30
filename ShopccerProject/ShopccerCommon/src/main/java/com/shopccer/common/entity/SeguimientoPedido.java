package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="seguimiento_pedido")
public class SeguimientoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idSeguimientoPedido;

    @Column(length = 256)
    private String notas;

    private Date updatedTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, nullable = false)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public Integer getIdSeguimientoPedido() {
        return idSeguimientoPedido;
    }

    public void setIdSeguimientoPedido(Integer idSeguimientoPedido) {
        this.idSeguimientoPedido = idSeguimientoPedido;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
