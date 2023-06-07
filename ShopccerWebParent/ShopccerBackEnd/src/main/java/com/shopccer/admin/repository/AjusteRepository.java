package com.shopccer.admin.repository;

import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteCategoria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AjusteRepository extends CrudRepository<Ajuste, String> {

    List<Ajuste> findByCategoria(AjusteCategoria ajusteCategoria);


}
