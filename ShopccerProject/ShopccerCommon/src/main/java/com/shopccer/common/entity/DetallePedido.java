package com.shopccer.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetallePedido;

    private int cantidad;
    private float costeProducto;
    private float costeEnvio;
    private float precioUnitario;
    private float subtotal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public DetallePedido() {
    }

    public DetallePedido(int cantidad, float costeProducto, float costeEnvio, float precioUnitario, float subtotal, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.costeProducto = costeProducto;
        this.costeEnvio = costeEnvio;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
        this.pedido = pedido;
    }

    public DetallePedido(Integer idDetallePedido, int cantidad, float costeProducto, float costeEnvio, float precioUnitario, float subtotal, Producto producto, Pedido pedido) {
        this.idDetallePedido = idDetallePedido;
        this.cantidad = cantidad;
        this.costeProducto = costeProducto;
        this.costeEnvio = costeEnvio;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
        this.pedido = pedido;
    }

    public Integer getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosteProducto() {
        return costeProducto;
    }

    public void setCosteProducto(float costeProducto) {
        this.costeProducto = costeProducto;
    }

    public float getCosteEnvio() {
        return costeEnvio;
    }

    public void setCosteEnvio(float costeEnvio) {
        this.costeEnvio = costeEnvio;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
