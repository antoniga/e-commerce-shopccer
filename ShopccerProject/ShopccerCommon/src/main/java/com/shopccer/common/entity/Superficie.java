package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="superficies")
public class Superficie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSuperficie;
    @Column(length = 128, nullable = false, unique = true)
    private String nombre;
    @Column(length = 128)
    private String foto;

    private Boolean activo;

    @OneToMany(mappedBy = "superficie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

    public Superficie() {
    }

    public Superficie(String nombre, String foto, Boolean activo) {
        this.nombre = nombre;
        this.foto = foto;
        this.activo = activo;
    }

    public Superficie(Integer idSuperficie, String nombre, String foto, Boolean activo) {
        this.idSuperficie = idSuperficie;
        this.nombre = nombre;
        this.foto = foto;
        this.activo = activo;
    }

    public Superficie(Integer idSuperficie, String nombre, String foto, Boolean activo, List<Producto> productos) {
        this.idSuperficie = idSuperficie;
        this.nombre = nombre;
        this.foto = foto;
        this.activo = activo;
        this.productos = productos;
    }

    public Superficie(String nombre, String foto, Boolean activo, List<Producto> productos) {
        this.nombre = nombre;
        this.foto = foto;
        this.activo = activo;
        this.productos = productos;
    }

    public Integer getIdSuperficie() {
        return idSuperficie;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setIdSuperficie(Integer idSuperficie) {
        this.idSuperficie = idSuperficie;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superficie that = (Superficie) o;
        return Objects.equals(idSuperficie, that.idSuperficie) && Objects.equals(nombre, that.nombre) && Objects.equals(foto, that.foto) && Objects.equals(activo, that.activo) && Objects.equals(productos, that.productos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSuperficie, nombre, foto, activo, productos);
    }

    @Override
    public String toString() {
        return "Superficie{" +
                "idSuperficie=" + idSuperficie +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", activo=" + activo +
                ", productos=" + productos +
                '}';
    }

    @Transient
    public String getPathFoto() {

        if(this.idSuperficie == null || foto == null) return"/images/default-superficie.png";

        return "/fotos-superficies/" + this.idSuperficie + "/" + this.foto;
    }
}
