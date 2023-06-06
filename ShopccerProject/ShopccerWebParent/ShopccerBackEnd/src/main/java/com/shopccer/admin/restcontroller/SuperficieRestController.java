package com.shopccer.admin.restcontroller;

import com.shopccer.admin.service.SuperficieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperficieRestController {

	@Autowired
	private SuperficieService superficieService;

	@PostMapping("/superficies/checknombre")
	public String checkNombreUnico(Integer idSuperficie, String nombre) {
		return superficieService.isNombreUnique(idSuperficie, nombre) ? "ok" : "duplicado";
	}

}
