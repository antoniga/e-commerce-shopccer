package com.shopccer.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopccer.admin.security.ShopccerUserDetails;
import com.shopccer.admin.service.UsuarioService;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.common.entity.Usuario;

@Controller
public class CuentaController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/cuenta")
	public String viewDetails(@AuthenticationPrincipal ShopccerUserDetails usuarioLogado, Model model) {

		String email = usuarioLogado.getUsername();

		Usuario usuario = usuarioService.findByEmail(email);

		model.addAttribute("usuario", usuario);

		return "usuarios/cuenta_form";
	}

	@PostMapping("/cuenta/update")
	public String updateDetails(Usuario usuario, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal ShopccerUserDetails usuarioLogado,
			@RequestParam("usuarioImg") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {

			String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			usuario.setFotos(nombreArchivo);
			Usuario savedUsuario = usuarioService.updateCuenta(usuario);
			String dirSubida = "fotos-usuarios/" + savedUsuario.getIdUsuario();

			FileLoadUtil.cleanDir(dirSubida);
			FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
		} else {

			if (usuario.getFotos().isEmpty()) {
				usuario.setFotos(null);
			}
			usuarioService.updateCuenta(usuario);
		}

		usuarioLogado.setNombre(usuario.getNombre());
		usuarioLogado.setApellidos(usuario.getApellidos());
		
		redirectAttributes.addFlashAttribute("msg", "Los datos de la cuenta han sido actualizados.");

		return "redirect:/cuenta";
	}

}
