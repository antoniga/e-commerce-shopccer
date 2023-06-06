package com.shopccer.admin.restcontroller;

import com.shopccer.admin.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarcaRestController {

	@Autowired
	private MarcaService marcaService;

	@PostMapping("/marcas/checknombre")
	public String checkNombreUnico(Integer idMarca, String nombre) {
		return marcaService.isNombreUnique(idMarca, nombre) ? "ok" : "duplicado";
	}

}
