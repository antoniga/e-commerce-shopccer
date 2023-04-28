package com.shopccer.admin.repository;

import com.shopccer.common.entity.Producto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer> {

    Producto findByNombre(String nombre);

    @Query("UPDATE Producto p SET p.activo = ?2 WHERE p.idProducto = ?1")
    @Modifying
    void updateProductoActivo(Integer id, Boolean activo);
}
