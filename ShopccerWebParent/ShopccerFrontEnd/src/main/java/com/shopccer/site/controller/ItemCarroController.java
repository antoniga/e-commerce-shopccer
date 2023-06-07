package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.site.service.ClienteService;
import com.shopccer.site.service.ItemCarroService;
import com.shopccer.site.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemCarroController {

    @Autowired private ClienteService clienteService;
    @Autowired private ItemCarroService itemCarroService;

    @GetMapping("/carro")
    public String viewCarro(Model model, HttpServletRequest request) {
        Cliente cliente = getClienteAutenticado(request);
        List<ItemCarro> itemsCarro = itemCarroService.listItemsCarro(cliente);

        Double totalEstimado = 0.0;

        for (ItemCarro item : itemsCarro) {
            totalEstimado += item.getSubtotal();
            totalEstimado = Math.round((totalEstimado) * 100.0) / 100.0;
        }

        model.addAttribute("itemsCarro", itemsCarro);
        model.addAttribute("totalEstimado", totalEstimado);

        return "carro/carro_compra";
    }

    private Cliente getClienteAutenticado(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return clienteService.findClienteByEmail(email);
    }
}
