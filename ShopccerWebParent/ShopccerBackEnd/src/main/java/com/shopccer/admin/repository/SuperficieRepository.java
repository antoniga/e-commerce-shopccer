package com.shopccer.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopccer.common.entity.Superficie;

public interface SuperficieRepository extends CrudRepository<Superficie, Integer>, PagingAndSortingRepository<Superficie, Integer> {

    Superficie findByNombre(String nombre);

    @Query("UPDATE Superficie s SET s.activo = ?2 WHERE s.idSuperficie = ?1")
    @Modifying
    void updateSuperficieActiva(Integer id, Boolean activo);

    @Query("SELECT s FROM Superficie s WHERE CONCAT(s.idSuperficie,' ',s.nombre) LIKE %?1%")
    Page<Superficie> findAll(String palabraClave, Pageable pageable);

}
