package com.shopccer.site.controller;

import com.shopccer.common.entity.Marca;
import com.shopccer.common.entity.Superficie;
import com.shopccer.site.service.MarcaService;
import com.shopccer.site.service.SuperficieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Controller
public class MainController {

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private SuperficieService superficieService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {

		List<Marca> listMarcas = marcaService.findAllEnable();
		List<Superficie> listSuperficies = superficieService.findAllEnable();

		model.addAttribute("listMarcas", listMarcas);
		model.addAttribute("listSuperficies", listSuperficies);
		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}

	@GetMapping("/contacto")
	public String viewContactoPage() {
		return "contacto";
	}

	@GetMapping("/acerca")
	public String viewAcercaPage() {
		return "acerca_de_nosotros";
	}

}
