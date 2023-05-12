package com.shopccer.admin.controller;

import com.shopccer.admin.service.AjusteService;
import com.shopccer.common.entity.Ajuste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AjusteController {

    @Autowired
    private AjusteService ajusteService;

    @GetMapping("/ajustes")
    public String listAll(Model model){

        List<Ajuste> listaAjustes = ajusteService.listAllAjustes();

        //listaAjustes.forEach(ajuste -> model.addAttribute(ajuste.getClave(), ajuste.getValue()));
        listaAjustes.forEach(ajuste -> System.out.println(ajuste.getClave() +" -- "+ ajuste.getValue()));

        return "ajustes/ajustes";
    }
}
