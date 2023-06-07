package com.shopccer.site.restcontroller;

import com.shopccer.site.repository.ComunidadRepository;
import com.shopccer.site.repository.PaisRepository;
import com.shopccer.site.dto.ComunidadDTO;
import com.shopccer.common.entity.Comunidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comunidades")
public class ComunidadRestController {

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private PaisRepository paisRepository;

    @GetMapping("/list_comunidades_by_pais/{idPais}")
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
