package com.shopccer.site.repository;

import com.shopccer.common.entity.Pais;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaisRepository extends CrudRepository<Pais,Integer> {

    List<Pais> findAllByOrderByNombreAsc();
}
