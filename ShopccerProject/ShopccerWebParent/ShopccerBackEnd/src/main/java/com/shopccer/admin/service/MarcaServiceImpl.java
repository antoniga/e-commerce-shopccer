package com.shopccer.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopccer.admin.exception.MarcaNotFoundException;
import com.shopccer.admin.repository.MarcaRepository;
import com.shopccer.common.entity.Marca;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaServiceImpl implements MarcaService {

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

}
