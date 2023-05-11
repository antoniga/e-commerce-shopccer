package com.shopccer.site.service;

import com.shopccer.site.exception.ProductoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shopccer.common.entity.Producto;

@Service
public interface ProductoService {
	
	Page<Producto> listByMarca(Integer idMarca, Integer numeroPagina);

	Page<Producto> listBySuperficie(Integer idSuperficie, Integer numeroPagina);

	Producto findById(Integer id) throws ProductoNotFoundException;

	Page<Producto> searchBypalabraClave(String palabraClave, Integer numeroPagina);

}
