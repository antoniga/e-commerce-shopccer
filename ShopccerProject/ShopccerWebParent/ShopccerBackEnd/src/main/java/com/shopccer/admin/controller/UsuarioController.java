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

import com.shopccer.admin.exception.UsuarioNotFoundException;
import com.shopccer.admin.service.RolService;
import com.shopccer.admin.service.UsuarioService;
import com.shopccer.admin.service.UsuarioServiceImpl;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.common.entity.Rol;
import com.shopccer.common.entity.Usuario;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/usuarios")
	public String listFirstPage(Model model) {
		
		return listByPage(1, model, "idUsuario", "asc", null);
	}
	
	@GetMapping("/usuarios/pagina/{numeroPagina}")
	public String listByPage(@PathVariable(name="numeroPagina") Integer numeroPagina,Model model,
			@Param("campoOrden") String campoOrden, @Param("dirOrden") String dirOrden,
			@Param("palabraClave") String palabraClave) {
		
		Page<Usuario> pagina = usuarioService.listByPage(numeroPagina, campoOrden, dirOrden, palabraClave);		
		List<Usuario> listaUsuarios = pagina.getContent();
		
		long startCount = (numeroPagina -1) * UsuarioServiceImpl.USUARIOS_POR_PAG + 1;
		long endCount = startCount + UsuarioServiceImpl.USUARIOS_POR_PAG - 1;
		
		if (endCount > pagina.getTotalElements()) {
			endCount = pagina.getTotalElements();
		}
		
		String dirOrdenContrario = ("asc").equals(dirOrden) ? "desc" : "asc";
		
		model.addAttribute("listaUsuarios",listaUsuarios);
		model.addAttribute("paginaActual",numeroPagina);
		model.addAttribute("paginasTotales",pagina.getTotalPages());
		model.addAttribute("startCount",startCount);
		model.addAttribute("endCount",endCount);
		model.addAttribute("usuariosTotales",pagina.getTotalElements());
		model.addAttribute("campoOrden",campoOrden);
		model.addAttribute("dirOrden",dirOrden);
		model.addAttribute("dirOrdenContrario",dirOrdenContrario);
		model.addAttribute("palabraClave",palabraClave);
		
		return "usuarios/usuarios";
	}
	
	@GetMapping("/usuarios/nuevo")
	public String addUsuario(Model model) {
		Usuario usuario = new Usuario();
		usuario.setActivo(true);
		List<Rol> listaRoles = rolService.listAll();
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaRoles", listaRoles);
		model.addAttribute("tituloPagina", "Crear nuevo usuario");
		
		return "usuarios/usuario_form";
	}
	
	@PostMapping("/usuarios/save")
	public String saveUsuario(Usuario usuario, RedirectAttributes redirectAttributes, @RequestParam("usuarioImg") MultipartFile multipartFile) throws IOException {
				
		if (!multipartFile.isEmpty()) {
			
			String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			usuario.setFotos(nombreArchivo);
			Usuario savedUsuario = usuarioService.save(usuario);
			String dirSubida = "fotos-usuarios/" + savedUsuario.getIdUsuario();		
			
			FileLoadUtil.cleanDir(dirSubida);
			FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
		}else {
			
			if (usuario.getFotos().isEmpty()) {
				usuario.setFotos(null);
			}
			usuarioService.save(usuario);			
		}
		
		redirectAttributes.addFlashAttribute("msg","El usuario ha sido guardado correctamente.");
		
		return getRedirectUrlToAffectedUsuario(usuario);
	}

	
	@GetMapping("/usuarios/edit/{idUsuario}")
	public String editUsuario(@PathVariable(name="idUsuario") Integer idUsuario, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			
			Usuario usuario = usuarioService.findById(idUsuario);
			List<Rol> listaRoles = rolService.listAll();
			model.addAttribute("usuario", usuario);
			model.addAttribute("listaRoles", listaRoles);
			model.addAttribute("tituloPagina", "Editar usuario (Id: "+idUsuario+") ");
			return "usuarios/usuario_form";
		} catch (UsuarioNotFoundException e) {

			redirectAttributes.addFlashAttribute("msg",e.getMessage());
			return "redirect:/usuarios";
		}
		
	}
	
	@GetMapping("/usuarios/delete/{idUsuario}")
	public String deleteUsuario(@PathVariable(name = "idUsuario") Integer idUsuario, Model model, RedirectAttributes redirectAttributes) {
		
		try {

			usuarioService.deleteByID(idUsuario);
			redirectAttributes.addFlashAttribute("msg", "El usuario con id: " + idUsuario + " ha sido eliminado.");
		} catch (UsuarioNotFoundException e) {

			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		
		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuarios/{idUsuario}/activo/{bool}")
	public String updateUsuarioActivo(@PathVariable(name = "idUsuario") Integer idUsuario,
			@PathVariable(name = "bool") Boolean activo, RedirectAttributes redirectAttributes) {

		usuarioService.updateUsuarioActivo(idUsuario, activo);
		String msgActivo = activo ? "activado" : "desactivado";
		String msg = "El usuario con id: " + idUsuario + " ha sido " + msgActivo;
		
		redirectAttributes.addFlashAttribute("msg", msg);
		
		return "redirect:/usuarios";
	}
	

	/**
	 * Para redirigir la vista de usuarios tras actualizar o crear un nuevo usuario
	 * y que solo se muestre el usuario afectado.
	 * @param usuario
	 * @return url
	 */
	private String getRedirectUrlToAffectedUsuario(Usuario usuario) {
		String splitEmail = usuario.getEmail().split("@")[0];
		
		return "redirect:/usuarios/pagina/1?campoOrden=idUsuario&dirOrden=asc&palabraClave=" + splitEmail;
	}
		
	

}
