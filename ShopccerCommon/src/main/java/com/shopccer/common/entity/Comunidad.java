package com.shopccer.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comunidades")
public class Comunidad {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idComunidad;
        @Column(length = 45, nullable = false)
        private String nombre;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_pais")
        private Pais pais;

    public Comunidad() {
    }

    public Comunidad(String nombre, Pais pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Comunidad{" +
                "idComunidad=" + idComunidad +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
