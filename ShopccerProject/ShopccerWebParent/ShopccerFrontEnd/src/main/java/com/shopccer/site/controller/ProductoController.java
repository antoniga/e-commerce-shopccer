package com.shopccer.site.controller;

import com.shopccer.common.entity.Marca;
import com.shopccer.site.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/m/{idMarca}")
    public String viewMarcas(@PathVariable("idMarca") Integer idMarca, Model model){

        Marca marca = marcaService.findByIdEnable(idMarca);

        if(marca == null) return "error/404";

        model.addAttribute("tituloPagina",marca.getNombre());



        return "productos_por_marca";
    }
}
