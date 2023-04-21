package com.shopccer.admin.service.impl;

import java.util.List;

import com.shopccer.admin.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopccer.admin.exception.MarcaNotFoundException;
import com.shopccer.admin.repository.MarcaRepository;
import com.shopccer.common.entity.Marca;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaServiceImpl implements MarcaService {
	
	public static final Integer MARCAS_POR_PAG = 4;

	@Autowired
	private MarcaRepository marcaRepository;

	@Override
	public List<Marca> listAll() {

		return (List<Marca>) marcaRepository.findAll();
	}

	@Override
	public Marca save(Marca marca) {
		return marcaRepository.save(marca);
	}

	@Override
	public Marca findById(Integer id) throws MarcaNotFoundException {
		return marcaRepository.findById(id)
				.orElseThrow(() -> new MarcaNotFoundException("No existe ninguna marca con ese id: " + id));
	}

	@Override
	public Boolean isNombreUnique(Integer id, String nombre) {

		/** Buscamos la marca por el nombre */
		Marca marcaNombre = marcaRepository.findByNombre(nombre);

		/** Si no existe, es null, por lo que el nombre es unico en bd */
		if (marcaNombre == null)
			return true;

		/**
		 * Si existe el nombre, miramos si se trata de nueva marca o edicion comprobando
		 * si el id es null -> nueva marca
		 */
		boolean isNuevaMarca = (id == null);

		/** Si es nueva marca */
		if (isNuevaMarca) {
			/** Y ya existe ese nombre, false */
			if (marcaNombre != null)
				return false;

		} else {
			if (marcaNombre.getIdMarca() != id)
				return false;
		}
		return true;
	}

	@Override
	public void deleteById(Integer id) throws MarcaNotFoundException {
		if (marcaRepository.findById(id).isPresent()) {
			marcaRepository.deleteById(id);
		} else {
			throw new MarcaNotFoundException("No existe ninguna marca con ese id: " + id);
		}

	}

	@Override
	public void updateMarcaActiva(Integer id, Boolean activo) {
		
		marcaRepository.updateMarcaActiva(id, activo);
		
	}

	@Override
	public Page<Marca> listByPage(Integer numeroPagina, String campoOrden, String dirOrden, String palabraClave) {
		
		Sort sort = Sort.by(campoOrden);

		sort = ("asc").equals(dirOrden) ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(numeroPagina - 1, MARCAS_POR_PAG, sort);
		
		if (palabraClave != null) {

			return marcaRepository.findAll(palabraClave, pageable);
		}
		
		return marcaRepository.findAll(pageable);
	}

}
