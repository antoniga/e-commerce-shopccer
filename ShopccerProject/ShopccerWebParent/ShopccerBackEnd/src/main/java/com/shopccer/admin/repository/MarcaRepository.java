package com.shopccer.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopccer.common.entity.Marca;

public interface MarcaRepository extends CrudRepository<Marca, Integer>, PagingAndSortingRepository<Marca, Integer>{

}
