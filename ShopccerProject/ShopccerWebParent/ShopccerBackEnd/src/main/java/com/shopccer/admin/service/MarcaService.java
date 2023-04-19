package com.shopccer.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shopccer.admin.exception.MarcaNotFoundException;
import com.shopccer.common.entity.Marca;

@Service
public interface MarcaService {
	
	public List<Marca> listAll();
	
	public Marca save(Marca marca);
	
	public Marca findById(Integer id) throws MarcaNotFoundException;

	public Boolean isNombreUnique(Integer id, String nombre);
	
	public void deleteById(Integer id) throws MarcaNotFoundException;
	
	public void updateMarcaActiva(Integer id, Boolean activo);
	
	public Page<Marca> listByPage(Integer numeroPagina, String campoOrden, String dirOrden, String palabraClave);

}
