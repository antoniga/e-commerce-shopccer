package com.shopccer.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopccer.common.entity.Marca;

public interface MarcaRepository extends CrudRepository<Marca, Integer>, PagingAndSortingRepository<Marca, Integer>{
	
	public Marca findByNombre(String nombre);
	
	@Query("UPDATE Marca m SET m.activo = ?2 WHERE m.idMarca = ?1")
	@Modifying
	public void updateMarcaActiva(Integer id, Boolean activo);
	
	@Query("SELECT m FROM Marca m WHERE CONCAT(m.idMarca,' ',m.nombre) LIKE %?1%")
	public Page<Marca> findAll(String palabraClave, Pageable pageable);
	
}
