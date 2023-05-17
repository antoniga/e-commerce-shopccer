package com.shopccer.admin.restcontroller;

import com.shopccer.admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteRestController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/clientes/checkemail")
	public String checkDuplicateEmail(@Param("idCliente") Integer idCliente, @Param("email") String email) {
		return clienteService.isEmailUnique(idCliente,email) ? "ok" : "duplicado";
	}

}
