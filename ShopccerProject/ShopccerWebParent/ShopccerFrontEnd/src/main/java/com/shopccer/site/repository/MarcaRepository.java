package com.shopccer.site.repository;

import com.shopccer.common.entity.Marca;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarcaRepository extends CrudRepository<Marca, Integer> {
    @Query("SELECT m FROM Marca m WHERE m.activo = true ORDER BY m.nombre ASC")
    List<Marca> findAllEnabled();


}
