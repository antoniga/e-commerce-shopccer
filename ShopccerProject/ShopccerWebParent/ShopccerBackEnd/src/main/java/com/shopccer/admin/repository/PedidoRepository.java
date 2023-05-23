package com.shopccer.admin.repository;

import com.shopccer.common.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Integer>, PagingAndSortingRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.nombre LIKE %?1% OR"
            + " p.apellidos LIKE %?1% OR p.numeroTelefono LIKE %?1% OR"
            + " p.direccion LIKE %?1% OR"
            + " p.codPostal LIKE %?1% OR p.ciudad LIKE %?1% OR"
            + " p.comunidad LIKE %?1% OR p.pais LIKE %?1% OR"
            + " p.estado LIKE %?1% OR"
            + " p.cliente.nombre LIKE %?1% OR"
            + " p.cliente.apellidos LIKE %?1%")
    public Page<Pedido> findAll(String palabraClave, Pageable pageable);
}
