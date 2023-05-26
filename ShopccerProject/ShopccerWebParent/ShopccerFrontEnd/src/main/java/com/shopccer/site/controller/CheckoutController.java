package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.site.checkout.CheckoutInfo;
import com.shopccer.site.service.CheckOutService;
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
public class CheckoutController {

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private ItemCarroService itemCarroService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        Cliente cliente = getClienteAutenticado(request);

        List<ItemCarro> itemsCarro = itemCarroService.listItemsCarro(cliente);
        CheckoutInfo checkoutInfo = checkOutService.prepareCheckout(itemsCarro);

        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("itemsCarro", itemsCarro);
        model.addAttribute("cliente", cliente);

        return "checkout/checkout";
    }

    private Cliente getClienteAutenticado(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return clienteService.findClienteByEmail(email);
    }
}
