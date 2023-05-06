package com.shopccer.admin.service;


import com.shopccer.admin.exception.ProductoNotFoundException;
import com.shopccer.common.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductoService {

    List<Producto> listAll();

    Producto save(Producto producto);

    Producto findById(Integer id) throws ProductoNotFoundException;

    Boolean isNombreUnique(Integer id, String nombre);

    void deleteById(Integer id) throws ProductoNotFoundException;

    void updateProductoActivo(Integer id, Boolean activo);

    Page<Producto> listByPage(Integer numeroPagina, String campoOrden, String dirOrden, String palabraClave);
}
