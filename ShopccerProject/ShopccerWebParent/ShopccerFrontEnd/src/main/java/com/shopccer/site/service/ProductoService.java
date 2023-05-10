package com.shopccer.site.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shopccer.common.entity.Producto;

@Service
public interface ProductoService {
	
	Page<Producto> listByMarca(Integer idMarca, Integer numeroPagina);

}
