package com.shopccer.admin.repository;

import com.shopccer.common.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer> {

    Producto findByNombre(String nombre);

    @Query("UPDATE Producto p SET p.activo = ?2 WHERE p.idProducto = ?1")
    @Modifying
    void updateProductoActivo(Integer id, Boolean activo);
    @Query("SELECT p FROM Producto p WHERE CONCAT(p.idProducto,' ',p.nombre,' ',p.marca.nombre,' ',p.superficie.nombre) LIKE %?1% ")
    public Page<Producto> findAll(String palabraClave, Pageable pageable);
}
