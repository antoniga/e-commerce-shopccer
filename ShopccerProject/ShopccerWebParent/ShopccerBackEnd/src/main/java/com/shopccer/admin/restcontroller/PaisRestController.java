package com.shopccer.admin.restcontroller;

import com.shopccer.admin.repository.PaisRepository;
import com.shopccer.common.entity.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paises")
public class PaisRestController {

    @Autowired
    private PaisRepository paisRepository;

    @GetMapping("/list")
    public List<Pais> listAll(){
        paisRepository.findAllByOrderByNombreAsc().forEach(pais -> System.out.println(pais.getNombre()));
        return paisRepository.findAllByOrderByNombreAsc();
    }

    @PostMapping("/save")
    public String save(@RequestBody Pais pais){
        Pais paisSaved = paisRepository.save(pais);
        return String.valueOf(paisSaved.getIdPais());
    }

    @DeleteMapping("/delete/{idPais}")
    public void deleteById(@PathVariable("idPais") Integer idPais){
        paisRepository.deleteById(idPais);
    }
}
