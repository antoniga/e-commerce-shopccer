package com.shopccer.site.service.impl;

import com.shopccer.site.exception.ProductoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopccer.common.entity.Producto;
import com.shopccer.site.repository.ProductoRepository;
import com.shopccer.site.service.ProductoService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {
	
	public static final Integer PRODUCTOS_POR_PAG = 8;
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public Page<Producto> listByMarca(Integer idMarca, Integer numeroPagina) {

		Pageable pageable = PageRequest.of(numeroPagina - 1, PRODUCTOS_POR_PAG);
		
		return productoRepository.listByMarca(idMarca, pageable);
	}

	@Override
	public Page<Producto> listBySuperficie(Integer idSuperficie, Integer numeroPagina) {

		Pageable pageable = PageRequest.of(numeroPagina - 1, PRODUCTOS_POR_PAG);

		return productoRepository.listBySuperficie(idSuperficie, pageable);
	}

	@Override
	public Producto findById(Integer id) throws ProductoNotFoundException {
		return productoRepository.findById(id)
				.orElseThrow(() -> new ProductoNotFoundException("No existe un producto con ese id: "+id));
	}

}
