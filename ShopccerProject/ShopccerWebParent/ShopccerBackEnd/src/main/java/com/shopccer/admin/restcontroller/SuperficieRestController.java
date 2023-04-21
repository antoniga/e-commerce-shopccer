package com.shopccer.admin.restcontroller;

import com.shopccer.admin.service.SuperficieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperficieRestController {

	@Autowired
	private SuperficieService superficieService;

	@PostMapping("/superficies/checknombre")
	public String checkNombreUnico(@Param("idSuperficie") Integer idSuperficie, @Param("nombre") String nombre) {
		return superficieService.isNombreUnique(idSuperficie, nombre) ? "ok" : "duplicado";
	}

}
