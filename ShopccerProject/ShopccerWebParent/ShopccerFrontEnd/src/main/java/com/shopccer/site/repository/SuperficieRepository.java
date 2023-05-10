package com.shopccer.site.repository;

import com.shopccer.common.entity.Superficie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SuperficieRepository extends CrudRepository<Superficie, Integer> {

    @Query("SELECT s FROM Superficie s WHERE s.activo = true ORDER BY s.nombre ASC")
    List<Superficie> findAllEnabled();
    @Query("SELECT s FROM Superficie s WHERE s.activo = true AND s.idSuperficie = ?1")
    Superficie findByIdEnabled(Integer id);
}
