package com.shopccer.site.restcontroller;

import com.shopccer.site.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/clientes/check_unique_email")
    public String checkDuplicateEmail(@RequestParam("email") String email) {
        return clienteService.isEmailUnique(email) ? "ok" : "duplicado";
    }
}
