package com.shopccer.admin.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopccer.admin.service.UsuarioService;

@RestController
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService  usuarioService;
	
	@PostMapping("/usuarios/checkemail")
	public String checkDuplicateEmail(@Param("idUsuario") Integer idUsuario, @Param("email") String email) {
		return usuarioService.isEmailUnique(idUsuario,email) ? "ok" : "duplicado";
	}

}
