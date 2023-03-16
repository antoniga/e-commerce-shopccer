package com.shopccer.common.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;

	@Column(length = 128, nullable = false, unique = true)
	private String email;

	@Column(length = 64, nullable = false)
	private String passsword;

	@Column(length = 45, nullable = false)
	private String nombre;

	@Column(length = 45, nullable = false)
	private String apellidos;

	@Column(length = 64)
	private String fotos;

	private Boolean activo;

	@ManyToOne
	@JoinColumn(name = "idRol")
	private Rol rol;

	public Usuario() {
		super();
	}

	public Usuario(String email, String passsword, String nombre, String apellidos, String fotos, Boolean activo,
			Rol rol) {
		super();
		this.email = email;
		this.passsword = passsword;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fotos = fotos;
		this.activo = activo;
		this.rol = rol;
	}

	public Usuario(Integer idUsuario, String email, String passsword, String nombre, String apellidos, String fotos,
			Boolean activo, Rol rol) {
		super();
		this.idUsuario = idUsuario;
		this.email = email;
		this.passsword = passsword;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fotos = fotos;
		this.activo = activo;
		this.rol = rol;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasssword() {
		return passsword;
	}

	public void setPasssword(String passsword) {
		this.passsword = passsword;
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

	public String getFotos() {
		return fotos;
	}

	public void setFotos(String fotos) {
		this.fotos = fotos;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activo, apellidos, email, fotos, idUsuario, nombre, passsword, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(activo, other.activo) && Objects.equals(apellidos, other.apellidos)
				&& Objects.equals(email, other.email) && Objects.equals(fotos, other.fotos)
				&& Objects.equals(idUsuario, other.idUsuario) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(passsword, other.passsword) && Objects.equals(rol, other.rol);
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", email=" + email + ", passsword=" + passsword + ", nombre="
				+ nombre + ", apellidos=" + apellidos + ", fotos=" + fotos + ", activo=" + activo + ", rol=" + rol
				+ "]";
	}
	
	
	

}
