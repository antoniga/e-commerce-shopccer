package com.shopccer.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopccer.admin.service.MarcaService;
import com.shopccer.admin.utils.FileLoadUtil;
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
	
	@GetMapping("/marcas/nuevo")
	public String addMarca(Model model) {
		Marca marca = new Marca();
		marca.setActivo(true);
		model.addAttribute("marca", marca);
		model.addAttribute("tituloPagina","Crear nueva marca");
		return "marcas/marca_form";
	}
	
	@PostMapping("/marcas/save")
	public String saveMarca(Marca marca,@RequestParam("marcaImg") MultipartFile multipartFile,  RedirectAttributes redirectAttributes) throws IOException {
		
		String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		marca.setFoto(nombreArchivo);
		
		Marca savedMarca = marcaService.save(marca);

		String dirSubida = "../fotos-marcas/" + savedMarca.getIdMarca();
		FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
		
		redirectAttributes.addFlashAttribute("msg","La marca ha sido añadida correctamente");
		
		return "redirect:/marcas";
	}

}
