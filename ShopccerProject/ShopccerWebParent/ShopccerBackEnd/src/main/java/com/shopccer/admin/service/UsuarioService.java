package com.shopccer.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopccer.common.entity.Usuario;

@Service
public interface UsuarioService {
	
	public List<Usuario> listAll();
	
	public Usuario save(Usuario usuario);

}
