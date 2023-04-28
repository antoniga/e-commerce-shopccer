package com.shopccer.admin.service.impl;


import com.shopccer.admin.exception.ProductoNotFoundException;
import com.shopccer.admin.repository.ProductoRepository;
import com.shopccer.admin.service.ProductoService;
import com.shopccer.common.entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    public Producto save(Producto producto) {

        if (producto.getIdProducto() == null){
            producto.setCreatedTime(new Date());
        }

        producto.setUpdatedTime(new Date());

        return productoRepository.save(producto);
    }

    @Override
    public Producto findById(Integer id) throws ProductoNotFoundException {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("No existe un producto con ese id: "+id));
    }

    @Override
    public Boolean isNombreUnique(Integer id, String nombre) {


        /** Buscamos el producto por el nombre */
        Producto productoNombre = productoRepository.findByNombre(nombre);

        /** Si no existe, es null, por lo que el nombre es unico en bd */
        if (productoNombre == null)
            return true;

        /**
         * Si existe el nombre, miramos si se trata de nuevo producto o edicion comprobando
         * si el id es null -> nuevo producto
         */
        boolean isNuevoProducto = (id == null);

        /** Si es nuevo producto */
        if (isNuevoProducto) {
            /** Y ya existe ese nombre, false */
            if (productoNombre != null)
                return false;

        } else {
            if (productoNombre.getIdProducto() != id)
                return false;
        }
        return true;
    }

    @Override
    public void deleteById(Integer id) throws ProductoNotFoundException {

        if (productoRepository.findById(id).isPresent()) {
            productoRepository.deleteById(id);
        } else {
            throw new ProductoNotFoundException("No existe ningun producto con ese id: " + id);
        }

    }

    @Override
    public void updateProductoActivo(Integer id, Boolean activo) {

        productoRepository.updateProductoActivo(id, activo);
    }
}
