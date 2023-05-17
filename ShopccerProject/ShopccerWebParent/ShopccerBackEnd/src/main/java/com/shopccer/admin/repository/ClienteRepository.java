package com.shopccer.admin.repository;

import com.shopccer.common.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>, PagingAndSortingRepository<Cliente, Integer> {


    @Query("SELECT c FROM Cliente c WHERE CONCAT(c.email, ' ', c.nombre, ' ', c.apellidos, ' ', "
            + "c.direccion, ' ', c.ciudad, ' ', c.comunidad, "
            + "' ', c.codPostal, ' ', c.pais.nombre) LIKE %?1%")
    Page<Cliente> findAll(String palabraClave, Pageable pageable);

    @Query("UPDATE Cliente c SET c.activo = ?2 WHERE c.idCliente = ?1")
    @Modifying
    void updateEnabledStatus(Integer idCliente, boolean activo);

    @Query("SELECT c FROM Cliente c WHERE c.email = ?1")
    Cliente findByEmail(String email);

    Long countByIdCliente(Integer idCliente);
}
