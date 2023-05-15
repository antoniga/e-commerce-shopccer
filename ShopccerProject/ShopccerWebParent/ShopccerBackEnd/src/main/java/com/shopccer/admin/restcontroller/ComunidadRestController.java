package com.shopccer.admin.restcontroller;

import com.shopccer.admin.repository.ComunidadRepository;
import com.shopccer.admin.repository.PaisRepository;
import com.shopccer.admin.utils.ComunidadDTO;
import com.shopccer.common.entity.Comunidad;
import com.shopccer.common.entity.Pais;
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

    @GetMapping("/list_by_pais/{idPais}")
    public List<ComunidadDTO> listByCountry(@PathVariable("idPais") Integer idPais) {
        List<Comunidad> listaComunidades = comunidadRepository.findByPaisOrderByNombreAsc(paisRepository.findById(idPais).get());
        listaComunidades.forEach(comunidad -> System.out.println(comunidad.getNombre()));
        List<ComunidadDTO> result = new ArrayList<>();

        for (Comunidad comunidad : listaComunidades) {
            result.add(new ComunidadDTO(comunidad.getIdComunidad(), comunidad.getNombre()));
        }

        return result;
    }

    @PostMapping("/save")
    public String save(@RequestBody Comunidad comunidad) {
        Comunidad savedComunidad = comunidadRepository.save(comunidad);
        return String.valueOf(savedComunidad.getIdComunidad());
    }

    @DeleteMapping("/delete/{idComunidad}")
    public void delete(@PathVariable("idComunidad") Integer idComunidad) {
        comunidadRepository.deleteById(idComunidad);
    }


}
