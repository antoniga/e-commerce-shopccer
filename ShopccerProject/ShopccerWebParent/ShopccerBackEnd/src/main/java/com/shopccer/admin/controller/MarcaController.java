package com.shopccer.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopccer.admin.service.MarcaService;
import com.shopccer.common.entity.Marca;

@Controller
public class MarcaController {
	
	@Autowired
	private MarcaService marcaService;
	
	@GetMapping("/marcas")
	public String listFirstPage(Model model) {
		
		List<Marca> listaMarcas = marcaService.listAll();
		
		model.addAttribute("listaMarcas", listaMarcas);
		
		return "marcas/marcas";
	}

}
