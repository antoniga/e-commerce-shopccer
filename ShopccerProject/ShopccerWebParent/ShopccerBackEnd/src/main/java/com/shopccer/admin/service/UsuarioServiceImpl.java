package com.shopccer.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopccer.admin.repository.UsuarioRepository;
import com.shopccer.common.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> listAll(){
		return (List<Usuario>) usuarioRepository.findAll();
	}

	public Usuario save(Usuario usuario) {
		encodePassword(usuario);
		return usuarioRepository.save(usuario);
	}
	
	private void encodePassword(Usuario usuario) {
		
		String encodedPwd = passwordEncoder.encode(usuario.getpassword());
		usuario.setpassword(encodedPwd);
	}

}
