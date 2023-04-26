package com.shopccer.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopccer.common.entity.Marca;

import java.util.List;

public interface MarcaRepository extends CrudRepository<Marca, Integer>, PagingAndSortingRepository<Marca, Integer>{
	
	Marca findByNombre(String nombre);
	
	@Query("UPDATE Marca m SET m.activo = ?2 WHERE m.idMarca = ?1")
	@Modifying
	void updateMarcaActiva(Integer id, Boolean activo);
	
	@Query("SELECT m FROM Marca m WHERE CONCAT(m.idMarca,' ',m.nombre) LIKE %?1%")
	Page<Marca> findAll(String palabraClave, Pageable pageable);

	@Query("SELECT new Marca(m.idMarca, m.nombre) FROM Marca m ORDER BY m.nombre ASC")
	List<Marca> findAll();
	
}
