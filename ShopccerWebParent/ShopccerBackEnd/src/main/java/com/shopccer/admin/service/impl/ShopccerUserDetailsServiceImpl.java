package com.shopccer.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopccer.admin.repository.UsuarioRepository;
import com.shopccer.admin.security.ShopccerUserDetails;
import com.shopccer.common.entity.Usuario;

public class ShopccerUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario != null) {
			return new ShopccerUserDetails(usuario);
		}

		throw new UsernameNotFoundException("No se pudo encontrar un usuario con el siguiente email:" + email);
	}

}
