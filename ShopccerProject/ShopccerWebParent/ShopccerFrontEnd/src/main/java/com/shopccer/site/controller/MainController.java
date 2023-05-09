package com.shopccer.site.controller;

import com.shopccer.common.entity.Marca;
import com.shopccer.site.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

	@Autowired
	private MarcaService marcaService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {

		List<Marca> listMarcas = marcaService.findAllEnable();

		model.addAttribute("listMarcas", listMarcas);
		return "index";
	}

}
