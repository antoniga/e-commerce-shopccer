package com.shopccer.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopccer.admin.exception.MarcaNotFoundException;
import com.shopccer.common.entity.Marca;

@Service
public interface MarcaService {
	
	public List<Marca> listAll();
	
	public Marca save(Marca marca);
	
	public Marca findById(Integer id) throws MarcaNotFoundException;

}
