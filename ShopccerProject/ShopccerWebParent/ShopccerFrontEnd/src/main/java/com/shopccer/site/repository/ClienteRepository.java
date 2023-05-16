package com.shopccer.site.repository;

import com.shopccer.common.entity.Cliente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.email = ?1")
    Cliente findByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.codigoVerificacion = ?1")
    Cliente findByCodigoVerificacion(String code);

    @Query("UPDATE Cliente c SET c.activo = true, c.codigoVerificacion = null  WHERE c.idCliente = ?1")
    @Modifying
    void enable(Integer id);
}
