package com.shopccer.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopccer.admin.service.RolService;
import com.shopccer.admin.service.UsuarioService;
import com.shopccer.common.entity.Rol;
import com.shopccer.common.entity.Usuario;

@Controller
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/usuarios")
	public String listAll(Model model) {
		
		List<Usuario> listaUsuarios = usuarioService.listAll();
		model.addAttribute("listaUsuarios",listaUsuarios);
		return "usuarios";
	}
	
	@GetMapping("/usuarios/nuevo")
	public String addUsuario(Model model) {
		Usuario usuario = new Usuario();
		usuario.setActivo(true);
		List<Rol> listaRoles = rolService.listAll();
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaRoles", listaRoles);
		
		return "usuario_form";
	}
	
	@PostMapping("usuarios/save")
	public String saveUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
		System.out.println(usuario.toString());
		usuarioService.save(usuario);
		
		redirectAttributes.addFlashAttribute("msg","El usuario ha sido guardado correctamente.");
		return "redirect:/usuarios";
	}

}
