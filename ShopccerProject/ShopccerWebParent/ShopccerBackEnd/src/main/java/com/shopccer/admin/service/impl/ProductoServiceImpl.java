package com.shopccer.admin.service.impl;

import com.shopccer.admin.repository.ProductoRepository;
import com.shopccer.admin.service.ProductoService;
import com.shopccer.common.entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
