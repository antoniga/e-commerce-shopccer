package com.shopccer.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items_carrito")
public class ItemCarro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItemCarro;
    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name="id_cliente")
    private Cliente cliente;

    private Integer cantidad;

    public ItemCarro() {
    }

    public ItemCarro(Integer idItemCarro, Producto producto, Cliente cliente, Integer cantidad) {
        this.idItemCarro = idItemCarro;
        this.producto = producto;
        this.cliente = cliente;
        this.cantidad = cantidad;
    }

    public Integer getIdItemCarro() {
        return idItemCarro;
    }

    public void setIdItemCarro(Integer idItemCarro) {
        this.idItemCarro = idItemCarro;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ItemCarro{" +
                "idItemCarro=" + idItemCarro +
                ", producto=" + producto.getDescripcion() +
                ", cliente=" + cliente.getNombre() +
                ", cantidad=" + cantidad +
                '}';
    }
}