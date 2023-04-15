package com.shopccer.common.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "marcas")
public class Marca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMarca;

	@Column(length = 128, nullable = false, unique = true)
	private String nombre;

	@Column(length = 128, nullable = false)
	private String foto;

	private Boolean activo;

	public Marca() {
		super();
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

	@Override
	public int hashCode() {
		return Objects.hash(activo, foto, idMarca, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marca other = (Marca) obj;
		return Objects.equals(activo, other.activo) && Objects.equals(foto, other.foto)
				&& Objects.equals(idMarca, other.idMarca) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Marca [idMarca=" + idMarca + ", nombre=" + nombre + ", foto=" + foto + ", activo=" + activo + "]";
	}

	@Transient
	public String getPathFoto() {
		return "/fotos-marcas/" + this.idMarca + "/" + this.foto;
	}

}
