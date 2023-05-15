package com.shopccer.admin.repository;

import com.shopccer.common.entity.Comunidad;
import com.shopccer.common.entity.Pais;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComunidadRepository extends CrudRepository<Comunidad,Integer> {

    List<Comunidad> findByPaisOrderByNombreAsc(Pais pais);
}
