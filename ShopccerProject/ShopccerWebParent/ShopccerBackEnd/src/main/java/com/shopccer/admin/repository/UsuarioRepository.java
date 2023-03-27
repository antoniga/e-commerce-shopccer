package com.shopccer.admin.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopccer.common.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>, PagingAndSortingRepository<Usuario, Integer>{
	
	public Usuario findByEmail(String email);
	
	@Query("UPDATE Usuario u SET u.activo = ?2 WHERE u.idUsuario = ?1")
	@Modifying
	public void updateUsuarioActivo(Integer id, Boolean activo);

}
