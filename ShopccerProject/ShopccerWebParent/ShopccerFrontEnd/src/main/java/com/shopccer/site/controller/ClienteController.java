package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import com.shopccer.site.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/registro")
    public String showFormRegistro(Model model) {
        List<Pais> listCountries = clienteService.listAllPaises();

        model.addAttribute("listaPaises", listCountries);
        model.addAttribute("tituloPagina", "Registro Cliente");
        model.addAttribute("cliente", new Cliente());

        return "registro/registro_form";
    }

    @PostMapping("/save_cliente")
    public String createCliente(Cliente cliente, Model model,
                                 HttpServletRequest request) {
        //clienteService.registroCliente(cliente);
        //sendVerificationEmail(request, cliente);

        model.addAttribute("tituloPagina", "¡Registro efectuado con éxito!");

        return "registro/registro_success";
    }
}
