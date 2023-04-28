package com.shopccer.admin.service;

import com.shopccer.common.entity.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductoService {

    List<Producto> listAll();

    Producto save(Producto producto);
}
