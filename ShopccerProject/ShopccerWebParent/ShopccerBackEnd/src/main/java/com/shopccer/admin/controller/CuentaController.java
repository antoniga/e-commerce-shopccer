package com.shopccer.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopccer.admin.security.ShopccerUserDetails;
import com.shopccer.admin.service.RolService;
import com.shopccer.admin.service.UsuarioService;
import com.shopccer.common.entity.Usuario;

@Controller
public class CuentaController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/cuenta")
	public String viewDetails(@AuthenticationPrincipal ShopccerUserDetails usuarioLogado, Model model) {
		
		String email = usuarioLogado.getUsername();
		
		Usuario usuario = usuarioService.findByEmail(email);		
		
		model.addAttribute("usuario", usuario);
		
		return "cuenta_form";
	}

}
