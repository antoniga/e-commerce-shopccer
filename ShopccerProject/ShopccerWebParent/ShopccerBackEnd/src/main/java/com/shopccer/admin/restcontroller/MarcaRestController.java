package com.shopccer.admin.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopccer.admin.service.MarcaService;

@RestController
public class MarcaRestController {

	@Autowired
	private MarcaService marcaService;

	@PostMapping("/marcas/checknombre")
	public String checkNombreUnico(@Param("idMarca") Integer idMarca, @Param("nombre") String nombre) {
		return marcaService.isNombreUnique(idMarca, nombre) ? "ok" : "duplicado";
	}

}
