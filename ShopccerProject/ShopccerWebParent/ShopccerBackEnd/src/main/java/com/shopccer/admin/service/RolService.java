package com.shopccer.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopccer.common.entity.Rol;

@Service
public interface RolService {
	
	public List<Rol> listAll();

}
