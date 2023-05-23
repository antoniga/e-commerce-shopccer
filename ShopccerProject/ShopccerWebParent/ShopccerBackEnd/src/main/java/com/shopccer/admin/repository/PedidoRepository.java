package com.shopccer.admin.repository;

import com.shopccer.common.entity.Pedido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Integer>, PagingAndSortingRepository<Pedido, Integer> {
}
