package com.shopccer.admin.restcontroller;

import com.shopccer.admin.repository.ComunidadRepository;
import com.shopccer.admin.repository.PaisRepository;
import com.shopccer.admin.service.ClienteService;
import com.shopccer.admin.utils.ComunidadDTO;
import com.shopccer.common.entity.Comunidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClienteRestController {
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ComunidadRepository comunidadRepository;

	@Autowired
	private PaisRepository paisRepository;
	
	@PostMapping("/clientes/checkemail")
	public String checkDuplicateEmail(@RequestParam("idCliente") Integer idCliente, @RequestParam("email") String email) {
		return clienteService.isEmailUnique(idCliente,email) ? "ok" : "duplicado";
	}

	@GetMapping("/clientes/list_comunidades_by_pais/{idPais}")
	public List<ComunidadDTO> listByCountry(@PathVariable("idPais") Integer idPais) {
		List<Comunidad> listaComunidades = comunidadRepository.findByPaisOrderByNombreAsc(paisRepository.findById(idPais).get());
		listaComunidades.forEach(comunidad -> System.out.println(comunidad.getNombre()));
		List<ComunidadDTO> result = new ArrayList<>();

		for (Comunidad comunidad : listaComunidades) {
			result.add(new ComunidadDTO(comunidad.getIdComunidad(), comunidad.getNombre()));
		}

		return result;
	}

}
