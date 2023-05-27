package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Pedido;
import com.shopccer.site.checkout.CheckoutInfo;
import com.shopccer.site.service.*;
import com.shopccer.site.util.EmailUtil;
import com.shopccer.site.util.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private ItemCarroService itemCarroService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private AjusteService ajusteService;

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

    @PostMapping("/realizar_pedido")
    public String realizarPedido(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        Cliente cliente = getClienteAutenticado(request);

        List<ItemCarro> itemsCarro = itemCarroService.listItemsCarro(cliente);
        CheckoutInfo checkoutInfo = checkOutService.prepareCheckout(itemsCarro);

        Pedido pedido = pedidoService.createPedido(cliente, itemsCarro, checkoutInfo);
        itemCarroService.deleteByCliente(cliente);
        sendEmailconfirmacion(request,pedido);

        return "checkout/pedido_completado";
    }

    private Cliente getClienteAutenticado(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return clienteService.findClienteByEmail(email);
    }

    private void sendEmailconfirmacion(HttpServletRequest request, Pedido pedido)
            throws UnsupportedEncodingException, MessagingException {
        EmailUtil ajustesEmail = ajusteService.getAjustesEmail();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(ajustesEmail);
        mailSender.setDefaultEncoding("utf-8");

        String toAddress = pedido.getCliente().getEmail();
        String subject = ajustesEmail.getOrderConfirmationSubject();
        String content = ajustesEmail.getOrderConfirmationContent();

        subject = subject.replace("[[idPedido]]", String.valueOf(pedido.getIdPedido()));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(ajustesEmail.getFromAddress(), ajustesEmail.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        DateFormat dateFormatter =  new SimpleDateFormat("E, dd MMM yyyy");
        String fechaEntrega = dateFormatter.format(pedido.getFechaEntrega());


        content = content.replace("[[nombre]]", pedido.getCliente().getNombreCompleto());
        content = content.replace("[[fechaEntrega]]", fechaEntrega);

        helper.setText(content, true);
        mailSender.send(message);
    }
}
