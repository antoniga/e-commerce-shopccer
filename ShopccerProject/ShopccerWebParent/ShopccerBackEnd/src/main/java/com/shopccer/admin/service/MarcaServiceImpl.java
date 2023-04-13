package com.shopccer.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopccer.admin.repository.MarcaRepository;
import com.shopccer.common.entity.Marca;

@Service
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

}