package com.shopccer.site.repository;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Producto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemCarroRepository extends CrudRepository<ItemCarro, Integer> {

    List<ItemCarro> findByCliente(Cliente cliente);

    ItemCarro findByClienteAndProducto(Cliente cliente, Producto producto);

    @Modifying
    @Query("UPDATE ItemCarro i SET i.cantidad = ?1 WHERE i.cliente.idCliente = ?2 AND i.producto.idProducto = ?3")
    void updateCanitdad(Integer cantidad, Integer idCliente, Integer idProducto);

    @Modifying
    @Query("DELETE FROM ItemCarro i WHERE i.cliente.idCliente = ?1 AND i.producto.idProducto = ?2")
    void deleteByClienteAndProducto(Integer idCliente, Integer idProducto);
}
