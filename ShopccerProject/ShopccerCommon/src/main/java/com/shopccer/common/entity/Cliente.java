package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    @Column(name = "numero_telefono", nullable = false, length = 15)
    private String numeroTelefono;

    @Column(nullable = false, length = 64)
    private String direccion;


    @Column(nullable = false, length = 45)
    private String ciudad;

    @Column(nullable = false, length = 45)
    private String comunidad;

    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codPostal;

    @Column(name = "codigo_verificacion", length = 64)
    private String codigoVerificacion;

    private boolean activo;

    @Column(name = "created_time")
    private Date createdTime;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

    @OneToMany(mappedBy = "cliente")
    private List<ItemCarro> itemsCarro;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;


    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(String email, String password, String nombre, String apellidos, String numeroTelefono, String direccion, String ciudad, String comunidad, String codPostal, String codigoVerificacion, boolean activo, Date createdTime, Pais pais) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.comunidad = comunidad;
        this.codPostal = codPostal;
        this.codigoVerificacion = codigoVerificacion;
        this.activo = activo;
        this.createdTime = createdTime;
        this.pais = pais;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public List<ItemCarro> getItemsCarro() {
        return itemsCarro;
    }

    public void setItemsCarro(List<ItemCarro> itemsCarro) {
        this.itemsCarro = itemsCarro;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }

    public String getNombreCompleto() {

        return nombre + " "+apellidos;
    }
}
