package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    @Column(name = "numeroTelefono", nullable = false, length = 15)
    private String numeroTelefono;

    @Column(name = "direccion", nullable = false, length = 64)
    private String direccion;

    @Column(nullable = false, length = 45)
    private String ciudad;

    @Column(nullable = false, length = 45)
    private String comunidad;

    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codPostal;

    @Column(nullable = false, length = 45)
    private String pais;

    private Date fechaPedido;

    private Double costeEnvio;
    private Double costeProducto;
    private Double total;
    private int diasEntrega;
    private Date fechaEntrega;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Set<DetallePedido> detallePedido = new HashSet<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL,  orphanRemoval = true)
    @OrderBy("updatedTime ASC")
    private List<SeguimientoPedido> seguimientosPedido = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(String nombre, String apellidos, String numeroTelefono, String direccion, String ciudad, String comunidad, String codPostal, String pais, Date fechaPedido, Double costeEnvio, Double costeProducto, Double total, int diasEntrega, Date fechaEntrega, EstadoPedido estado, Cliente cliente, Set<DetallePedido> detallePedido) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.comunidad = comunidad;
        this.codPostal = codPostal;
        this.pais = pais;
        this.fechaPedido = fechaPedido;
        this.costeEnvio = costeEnvio;
        this.costeProducto = costeProducto;
        this.total = total;
        this.diasEntrega = diasEntrega;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.cliente = cliente;
        this.detallePedido = detallePedido;
    }

    public Pedido(Integer idPedido, String nombre, String apellidos, String numeroTelefono, String direccion, String ciudad, String comunidad, String codPostal, String pais, Date fechaPedido, Double costeEnvio, Double costeProducto, Double total, int diasEntrega, Date fechaEntrega, EstadoPedido estado, Cliente cliente, Set<DetallePedido> detallePedido) {
        this.idPedido = idPedido;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.comunidad = comunidad;
        this.codPostal = codPostal;
        this.pais = pais;
        this.fechaPedido = fechaPedido;
        this.costeEnvio = costeEnvio;
        this.costeProducto = costeProducto;
        this.total = total;
        this.diasEntrega = diasEntrega;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.cliente = cliente;
        this.detallePedido = detallePedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getCosteEnvio() {
        return costeEnvio;
    }

    public void setCosteEnvio(Double costeEnvio) {
        this.costeEnvio = costeEnvio;
    }

    public Double getCosteProducto() {
        return costeProducto;
    }

    public void setCosteProducto(Double costeProducto) {
        this.costeProducto = costeProducto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getDiasEntrega() {
        return diasEntrega;
    }

    public void setDiasEntrega(int diasEntrega) {
        this.diasEntrega = diasEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<DetallePedido> getDetallesPedido() {
        return detallePedido;
    }

    public void setDetallesPedido(Set<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public Set<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(Set<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public List<SeguimientoPedido> getSeguimientosPedido() {
        return seguimientosPedido;
    }

    public void setSeguimientosPedido(List<SeguimientoPedido> seguimientosPedido) {
        this.seguimientosPedido = seguimientosPedido;
    }

    @Transient
    public String getDestino() {
        String destino =  ciudad + ", ";
        if (comunidad != null && !comunidad.isEmpty()) destino += comunidad + ", ";
        destino += pais;

        return destino;
    }

    @Transient
    public String getFechaEntregaOnForm() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(this.fechaEntrega);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", comunidad='" + comunidad + '\'' +
                ", codPostal='" + codPostal + '\'' +
                ", pais='" + pais + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", costeEnvio=" + costeEnvio +
                ", costeProducto=" + costeProducto +
                ", total=" + total +
                ", diasEntrega=" + diasEntrega +
                ", fechaEntrega=" + fechaEntrega +
                ", estado=" + estado +
                ", cliente=" + cliente.getNombre() +
                '}';
    }

    @Transient
    public String getNombreProductos() {
        String nombresProducto = "";

        nombresProducto = "<ul>";

        for (DetallePedido detalle : detallePedido) {
            nombresProducto += "<li>" + detalle.getProducto().getNombre() + "</li>";
        }

        nombresProducto += "</ul>";

        return nombresProducto;
    }

    @Transient
    public boolean isRecogido() {
        return hasEstado(EstadoPedido.RECOGIDO);
    }

    @Transient
    public boolean isEnviado() {
        return hasEstado(EstadoPedido.EN_TRANSITO);
    }

    @Transient
    public boolean isEnProceso() {
        return hasEstado(EstadoPedido.EN_PROCESO);
    }

    @Transient
    public boolean isEntregado() {
        return hasEstado(EstadoPedido.ENTREGADO);
    }

    @Transient
    public boolean isPeticionReembolso() {
        return hasEstado(EstadoPedido.PETICION_REEMBOLSO);
    }

    @Transient
    public boolean isReembolsado() {
        return hasEstado(EstadoPedido.REEMBOLSADO);
    }

    public boolean hasEstado(EstadoPedido estado) {
        for (SeguimientoPedido track : seguimientosPedido) {
            if (track.getEstado().equals(estado)) {
                return true;
            }
        }

        return false;
    }
}
