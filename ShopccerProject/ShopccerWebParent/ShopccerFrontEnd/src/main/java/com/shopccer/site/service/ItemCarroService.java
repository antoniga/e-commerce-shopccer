package com.shopccer.site.service;

import com.shopccer.common.entity.Cliente;
import com.shopccer.site.exception.ItemCarroException;
import org.springframework.stereotype.Service;

@Service
public interface ItemCarroService {

    Integer addProduct(Integer idProducto, Integer talla, Integer cantidad, Cliente cliente) throws ItemCarroException;
}
