package com.shopccer.site.repository;

import com.shopccer.common.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT DISTINCT p FROM Pedido p JOIN p.detallePedido pd JOIN pd.producto pr "
            + "WHERE p.cliente.idCliente = ?2 "
            + "AND (pr.nombre LIKE %?1% OR p.estado LIKE %?1%)")
    Page<Pedido> findAll(String palabraClave, Integer idCliente, Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.idCliente = ?1")
    Page<Pedido> findAll(Integer idCliente, Pageable pageable);
}
