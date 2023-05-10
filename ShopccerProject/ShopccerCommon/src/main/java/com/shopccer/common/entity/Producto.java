package com.shopccer.common.entity;

import jakarta.persistence.*;

import java.util.*;

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
    
    @ElementCollection
    private Map<Integer,Integer> tallaStock;
    private String color;
    @Column(length = 128, name="foto_principal", nullable = false)
    private String fotoPrincipal;
    @ElementCollection
    @Column(length = 128)
    private List<String> fotosDetalles;
    @Column(name="fecha_creacion", updatable = false)
    private Date createdTime;
    @Column(name="fecha_actualizacion")
    private Date updatedTime;
    private Boolean activo;
    @Column(name="en_stock")
    private Boolean inStock;
    private double precio;
    @Column(name="pocentaje_descuento")
    private double porcentajeDescuento;
    @ManyToOne
    @JoinColumn(name="idMarca")
    private Marca marca;
    @ManyToOne
    @JoinColumn(name="idSuperficie")
    private Superficie superficie;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Map<Integer, Integer> tallaStock, String color, String fotoPrincipal, List<String> fotosDetalles, Date createdTime, Date updatedTime, Boolean activo, Boolean inStock, double precio, double porcentajeDescuento, Marca marca, Superficie superficie) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tallaStock = tallaStock;
        this.color = color;
        this.fotoPrincipal = fotoPrincipal;
        this.fotosDetalles = fotosDetalles;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.activo = activo;
        this.inStock = inStock;
        this.precio = precio;
        this.porcentajeDescuento = porcentajeDescuento;
        this.marca = marca;
        this.superficie = superficie;
    }

    public Producto(Integer idProducto, String nombre, String descripcion, Map<Integer, Integer> tallaStock, String color, String fotoPrincipal, List<String> fotosDetalles, Date createdTime, Date updatedTime, Boolean activo, Boolean inStock, double precio, double porcentajeDescuento, Marca marca, Superficie superficie) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tallaStock = tallaStock;
        this.color = color;
        this.fotoPrincipal = fotoPrincipal;
        this.fotosDetalles = fotosDetalles;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.activo = activo;
        this.inStock = inStock;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
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

    public Map<Integer, Integer> getTallaStock() {
        return tallaStock;
    }

    public Integer getValue(Integer key){
        return this.tallaStock.get(key);
    }

    public void setTallaStock(Map<Integer, Integer> tallaStock) {
        this.tallaStock = tallaStock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public List<String> getFotosDetalles() {
        return fotosDetalles;
    }

    public void setFotosDetalles(List<String> fotosDetalles) {
        this.fotosDetalles = fotosDetalles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Double.compare(producto.precio, precio) == 0 && Double.compare(producto.porcentajeDescuento, porcentajeDescuento) == 0 && Objects.equals(idProducto, producto.idProducto) && Objects.equals(nombre, producto.nombre) && Objects.equals(descripcion, producto.descripcion) && Objects.equals(tallaStock, producto.tallaStock) && Objects.equals(color, producto.color) && Objects.equals(fotoPrincipal, producto.fotoPrincipal) && Objects.equals(fotosDetalles, producto.fotosDetalles) && Objects.equals(createdTime, producto.createdTime) && Objects.equals(updatedTime, producto.updatedTime) && Objects.equals(activo, producto.activo) && Objects.equals(inStock, producto.inStock) && Objects.equals(marca, producto.marca) && Objects.equals(superficie, producto.superficie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, nombre, descripcion, tallaStock, color, fotoPrincipal, fotosDetalles, createdTime, updatedTime, activo, inStock, precio, porcentajeDescuento, marca, superficie);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tallaStock=" + tallaStock +
                ", color='" + color + '\'' +
                ", fotoPrincipal='" + fotoPrincipal + '\'' +
                ", fotosDetalles=" + fotosDetalles +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", activo=" + activo +
                ", inStock=" + inStock +
                ", precio=" + precio +
                ", porcentajeDescuento=" + porcentajeDescuento +
                ", marca=" + marca.getNombre() +
                ", superficie=" + superficie.getNombre() +
                '}';
    }

    @Transient
    public String getPathFotoPrincipal() {

        if(this.idProducto == null || fotoPrincipal == null) return"/images/default-producto.png";

        return "/fotos-productos/" + this.idProducto + "/" + this.fotoPrincipal;
    }

    @Transient
    public String getPathFotosDetalle(String fotoDetalle) {

        return "/fotos-productos/" + this.idProducto + "/detalles/"+fotoDetalle;
    }

    public Boolean contieneFotoDetalle(String nombreArchivo){

        if(fotosDetalles != null){
            Iterator<String> iterator = fotosDetalles.iterator();

            while (iterator.hasNext()){
                String foto = iterator.next();
                if (foto.equals(nombreArchivo)){
                    return true;
                }
            }
        }
        return false;
    }

    @Transient
    public Double getPrecioConDescuento(){
        if (porcentajeDescuento > 0){
            return this.precio * ((100 - porcentajeDescuento) / 100);
        }
        return this.precio;
    }
}
