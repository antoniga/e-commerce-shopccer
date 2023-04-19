package com.shopccer.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopccer.admin.exception.MarcaNotFoundException;
import com.shopccer.admin.service.MarcaService;
import com.shopccer.admin.service.MarcaServiceImpl;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.common.entity.Marca;

@Controller
public class MarcaController {

	@Autowired
	private MarcaService marcaService;

	@GetMapping("/marcas")
	public String listFirstPage(Model model) {

		return listByPage(1, model, "idMarca", "asc", null);
	}
	
	@GetMapping("/marcas/pagina/{numeroPagina}")
	public String listByPage(@PathVariable(name="numeroPagina") Integer numeroPagina, Model model,
			@Param("campoOrden") String campoOrden, @Param("dirOrden") String dirOrden, 
			@Param("palabraClave") String palabraClave) {
		
		Page<Marca> pagina = marcaService.listByPage(numeroPagina, campoOrden, dirOrden, palabraClave);
		List<Marca> listaMarcas = pagina.getContent();
		
		long startCount = (numeroPagina -1) * MarcaServiceImpl.MARCAS_POR_PAG + 1;
		long endCount = startCount + MarcaServiceImpl.MARCAS_POR_PAG - 1;
		
		if (endCount > pagina.getTotalElements()) {
			endCount = pagina.getTotalElements();
		}
		
		String dirOrdenContrario = ("asc").equals(dirOrden) ? "desc" : "asc";
		
		model.addAttribute("listaMarcas",listaMarcas);
		model.addAttribute("paginaActual",numeroPagina);
		model.addAttribute("paginasTotales",pagina.getTotalPages());
		model.addAttribute("startCount",startCount);
		model.addAttribute("endCount",endCount);
		model.addAttribute("marcasTotales",pagina.getTotalElements());
		model.addAttribute("campoOrden",campoOrden);
		model.addAttribute("dirOrden",dirOrden);
		model.addAttribute("dirOrdenContrario",dirOrdenContrario);
		model.addAttribute("palabraClave",palabraClave);
		
		return "marcas/marcas";
		
	}

	@GetMapping("/marcas/nuevo")
	public String addMarca(Model model) {
		Marca marca = new Marca();
		marca.setActivo(true);
		model.addAttribute("marca", marca);
		model.addAttribute("tituloPagina", "Crear nueva marca");
		return "marcas/marca_form";
	}

	@PostMapping("/marcas/save")
	public String saveMarca(Marca marca, @RequestParam("marcaImg") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {

		if(!multipartFile.isEmpty()) {
			
			
			String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			marca.setFoto(nombreArchivo);
			
			Marca savedMarca = marcaService.save(marca);
			
			String dirSubida = "../fotos-marcas/" + savedMarca.getIdMarca();
			FileLoadUtil.cleanDir(dirSubida);
			FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
		} else {
			
			if(marca.getFoto().isEmpty()) {
				marca.setFoto(null);
			}
			
			marcaService.save(marca);
		}

		redirectAttributes.addFlashAttribute("msg", "La marca ha sido añadida correctamente");

		return getRedirectUrlToAffectedMarca(marca);
	}

	@GetMapping("/marcas/edit/{idMarca}")
	public String editMarca(@PathVariable(name = "idMarca") Integer idMarca, Model model,
			RedirectAttributes redirectAttributes) {

		try {

			Marca marca = marcaService.findById(idMarca);
			model.addAttribute("marca", marca);
			model.addAttribute("tituloPagina", "Editar marca (Id: " + idMarca + ") ");
			
			return "marcas/marca_form";
		} catch (MarcaNotFoundException e) {

			redirectAttributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/marcas";
		}

	}
	
	@GetMapping("/marcas/delete/{idMarca}")
	public String deleteMarca(@PathVariable(name = "idMarca") Integer idMarca, Model model,
			RedirectAttributes redirectAttributes) {

		try {
			marcaService.deleteById(idMarca);
			redirectAttributes.addFlashAttribute("msg", "La marca con id: " + idMarca + " ha sido eliminada.");
		} catch (MarcaNotFoundException e) {
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		
		return "redirect:/marcas";
	}
	
	@GetMapping("/marcas/{idMarca}/activo/{bool}")
	public String updateMarcaActivo(@PathVariable(name = "idMarca") Integer idMarca,
			@PathVariable(name = "bool") Boolean activo, RedirectAttributes redirectAttributes) {

		marcaService.updateMarcaActiva(idMarca, activo);
		String msgActivo = activo ? "activado" : "desactivado";
		String msg = "La marca con id: " + idMarca + " ha sido " + msgActivo;

		redirectAttributes.addFlashAttribute("msg", msg);

		return "redirect:/marcas";
	}	
	
	
	private String getRedirectUrlToAffectedMarca(Marca marca) {
		String nombre = marca.getNombre();
		return "redirect:/marcas/pagina/1?campoOrden=idMarca&dirOrden=asc&palabraClave=" + nombre;
	}
	

}
