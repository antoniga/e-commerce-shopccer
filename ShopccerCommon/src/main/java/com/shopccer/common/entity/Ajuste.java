package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="ajustes")
public class Ajuste {

    @Id
    @Column(nullable = false, length = 128)
    private String clave;

    @Column(nullable = false)
    private String valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private AjusteCategoria categoria;

    public Ajuste() {
    }

    public Ajuste(String clave){
        this.clave = clave;
    }

    public Ajuste(String clave, String valor, AjusteCategoria categoria) {
        this.clave = clave;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public AjusteCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(AjusteCategoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ajuste ajuste = (Ajuste) o;
        return Objects.equals(clave, ajuste.clave) && Objects.equals(valor, ajuste.valor) && categoria == ajuste.categoria;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave, valor, categoria);
    }
}
