package com.shopccer.admin.repository;

import com.shopccer.common.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer> {
}
