package com.shopccer.common.entity;

import jakarta.persistence.*;

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

    public String getValue() {
        return valor;
    }

    public void setValue(String valor) {
        this.valor = valor;
    }

    public AjusteCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(AjusteCategoria categoria) {
        this.categoria = categoria;
    }
}
