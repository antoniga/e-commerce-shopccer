package com.shopccer.site.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopccer.common.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer>{
	
	@Query("SELECT p FROM Producto p WHERE p.activo = true AND p.marca.idMarca = ?1 ")
	Page<Producto> listByMarca(Integer idMarca, Pageable pageable);

}
