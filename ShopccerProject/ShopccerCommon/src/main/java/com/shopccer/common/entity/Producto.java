package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    @Column(length = 256, nullable = false, unique = true)
    private String nombre;
    @Column(length = 4096)
    private String descripcion;
    @Column(name="fecha_creacion")
    private Date cratedTime;
    @Column(name="fecha_actualizacion")
    private Date updatedTime;
    private Boolean activo;
    @Column(name="en_stock")
    private Boolean inStock;
    private float coste;
    private float precio;
    @Column(name="pocentaje_descuento")
    private float porcentajeDescuento;
    @ManyToOne
    @JoinColumn(name="idMarca")
    private Marca marca;
    @ManyToOne
    @JoinColumn(name="idSuperficie")
    private Superficie superficie;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Date cratedTime, Date updatedTime, Boolean activo, Boolean inStock, float coste, float precio, float porcentajeDescuento, Marca marca, Superficie superficie) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cratedTime = cratedTime;
        this.updatedTime = updatedTime;
        this.activo = activo;
        this.inStock = inStock;
        this.coste = coste;
        this.precio = precio;
        this.porcentajeDescuento = porcentajeDescuento;
        this.marca = marca;
        this.superficie = superficie;
    }

    public Producto(Integer idProducto, String nombre, String descripcion, Date cratedTime, Date updatedTime, Boolean activo, Boolean inStock, float coste, float precio, float porcentajeDescuento, Marca marca, Superficie superficie) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cratedTime = cratedTime;
        this.updatedTime = updatedTime;
        this.activo = activo;
        this.inStock = inStock;
        this.coste = coste;
        this.precio = precio;
        this.porcentajeDescuento = porcentajeDescuento;
        this.marca = marca;
        this.superficie = superficie;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCratedTime() {
        return cratedTime;
    }

    public void setCratedTime(Date cratedTime) {
        this.cratedTime = cratedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(float porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Superficie getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Superficie superficie) {
        this.superficie = superficie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Float.compare(producto.coste, coste) == 0 && Float.compare(producto.precio, precio) == 0 && Float.compare(producto.porcentajeDescuento, porcentajeDescuento) == 0 && Objects.equals(idProducto, producto.idProducto) && Objects.equals(nombre, producto.nombre) && Objects.equals(descripcion, producto.descripcion) && Objects.equals(cratedTime, producto.cratedTime) && Objects.equals(updatedTime, producto.updatedTime) && Objects.equals(activo, producto.activo) && Objects.equals(inStock, producto.inStock) && Objects.equals(marca, producto.marca) && Objects.equals(superficie, producto.superficie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, nombre, descripcion, cratedTime, updatedTime, activo, inStock, coste, precio, porcentajeDescuento, marca, superficie);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cratedTime=" + cratedTime +
                ", updatedTime=" + updatedTime +
                ", activo=" + activo +
                ", inStock=" + inStock +
                ", coste=" + coste +
                ", precio=" + precio +
                ", porcentajeDescuento=" + porcentajeDescuento +
                ", marca=" + marca +
                ", superficie=" + superficie +
                '}';
    }
}
