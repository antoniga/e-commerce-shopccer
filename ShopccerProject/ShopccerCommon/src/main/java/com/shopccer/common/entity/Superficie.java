package com.shopccer.common.entity;

import jakarta.persistence.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superficie that)) return false;
        return Objects.equals(idSuperficie, that.idSuperficie) && Objects.equals(nombre, that.nombre) && Objects.equals(foto, that.foto) && Objects.equals(activo, that.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSuperficie, nombre, foto, activo);
    }

    @Override
    public String toString() {
        return "Superficie{" +
                "idSuperficie=" + idSuperficie +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", activo=" + activo +
                '}';
    }

    @Transient
    public String getPathFoto() {

        if(this.idSuperficie == null || foto == null) return"/images/default-superficie.png";

        return "/fotos-superficies/" + this.idSuperficie + "/" + this.foto;
    }
}
