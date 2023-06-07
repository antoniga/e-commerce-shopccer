package com.shopccer.admin.restcontroller;

import com.shopccer.admin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService  usuarioService;
	
	@PostMapping("/usuarios/checkemail")
	public String checkDuplicateEmail(Integer idUsuario, String email) {
		return usuarioService.isEmailUnique(idUsuario,email) ? "ok" : "duplicado";
	}

}
