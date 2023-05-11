package com.shopccer.site.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopccer.common.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer>{
	
	@Query("SELECT p FROM Producto p WHERE p.activo = true AND p.marca.idMarca = ?1 AND p.superficie.activo =true ORDER BY p.nombre ASC")
	Page<Producto> listByMarca(Integer idMarca, Pageable pageable);

	@Query("SELECT p FROM Producto p WHERE p.activo = true AND p.superficie.idSuperficie = ?1 AND p.marca.activo =true ORDER BY p.nombre ASC")
	Page<Producto> listBySuperficie(Integer idSuperficie, Pageable pageable);
	@Query(value="SELECT * FROM productos WHERE activo = true AND MATCH (nombre,descripcion) AGAINST (?1) ORDER BY nombre ASC ",
			nativeQuery = true)
	Page<Producto> searchBypalabraClave(String palabraClave, Pageable pageable);

}
