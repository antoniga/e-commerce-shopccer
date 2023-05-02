package com.shopccer.common.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "marcas")
public class Marca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMarca;

	@Column(length = 128, nullable = false, unique = true)
	private String nombre;

	@Column(length = 128)
	private String foto;

	private Boolean activo;

	@OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
	private List<Producto> productos;

	public Marca() {
		super();
	}

	public Marca(Integer idMarca, String nombre) {
		this.idMarca = idMarca;
		this.nombre = nombre;
	}

	public Marca(String nombre, String foto, Boolean activo) {
		super();
		this.nombre = nombre;
		this.foto = foto;
		this.activo = activo;
	}

	public Marca(Integer idMarca, String nombre, String foto, Boolean activo) {
		super();
		this.idMarca = idMarca;
		this.nombre = nombre;
		this.foto = foto;
		this.activo = activo;
	}

	public Marca(String nombre, String foto, Boolean activo, List<Producto> productos) {
		this.nombre = nombre;
		this.foto = foto;
		this.activo = activo;
		this.productos = productos;
	}

	public Marca(Integer idMarca, String nombre, String foto, Boolean activo, List<Producto> productos) {
		this.idMarca = idMarca;
		this.nombre = nombre;
		this.foto = foto;
		this.activo = activo;
		this.productos = productos;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Boolean getActivo() {
		return activo;
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
	public String toString() {
		return "Marca{" +
				"idMarca=" + idMarca +
				", nombre='" + nombre + '\'' +
				", foto='" + foto + '\'' +
				", activo=" + activo +
				", productos=" + productos +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Marca marca = (Marca) o;
		return Objects.equals(idMarca, marca.idMarca) && Objects.equals(nombre, marca.nombre) && Objects.equals(foto, marca.foto) && Objects.equals(activo, marca.activo) && Objects.equals(productos, marca.productos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idMarca, nombre, foto, activo, productos);
	}

	@Transient
	public String getPathFoto() {
		
		if(this.idMarca == null || foto == null) return"/images/default-marca.png";
		
		return "/fotos-marcas/" + this.idMarca + "/" + this.foto;
	}

}
