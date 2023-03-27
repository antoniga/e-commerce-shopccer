package com.shopccer.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopccer.admin.exception.UsuarioNotFoundException;
import com.shopccer.common.entity.Usuario;

@Service
public interface UsuarioService {
	
	public List<Usuario> listAll();
	
	public Usuario save(Usuario usuario);
	
	public Boolean isEmailUnique(Integer id, String email);
	
	public Usuario findById(Integer id) throws UsuarioNotFoundException;
	
	public void deleteByID(Integer id) throws UsuarioNotFoundException;
	
	public void updateUsuarioActivo(Integer id, Boolean activo);

}
