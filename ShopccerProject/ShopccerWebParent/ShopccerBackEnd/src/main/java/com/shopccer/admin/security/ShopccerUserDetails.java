package com.shopccer.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopccer.common.entity.Usuario;

public class ShopccerUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Usuario usuario;	

	public ShopccerUserDetails(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		
		authories.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));
		
		return authories;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.getActivo();
	}
	
	public String getFullname() {
		return this.usuario.getNombre()+" "+this.usuario.getApellidos();
	}

}
