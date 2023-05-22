package com.shopccer.site.service;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.site.exception.ItemCarroException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface ItemCarroService {

    Integer addProduct(Integer idProducto, Integer talla, Integer cantidad, Cliente cliente) throws ItemCarroException;

    public List<ItemCarro> listItemsCarro(Cliente cliente);

    Double updateCantidad(Integer idProducto, Integer talla, Integer cantidad, Cliente cliente, String variacionStock);

    void removeProducto(Cliente cliente, Integer idProducto,String talla);
}
