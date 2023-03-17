package com.shopccer.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopccer.admin.repository.RolRepository;
import com.shopccer.common.entity.Rol;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository rolRepository;
	
	public List<Rol> listAll() {
		return (List<Rol>) rolRepository.findAll();
	}

}
