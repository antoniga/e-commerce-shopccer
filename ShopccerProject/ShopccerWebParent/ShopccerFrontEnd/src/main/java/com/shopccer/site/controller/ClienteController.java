package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import com.shopccer.site.service.AjusteService;
import com.shopccer.site.service.ClienteService;
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
import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AjusteService ajusteService;

    @GetMapping("/registro")
    public String showFormRegistro(Model model) {
        List<Pais> listCountries = clienteService.listAllPaises();

        model.addAttribute("listaPaises", listCountries);
        model.addAttribute("tituloPagina", "Registro Cliente");
        model.addAttribute("cliente", new Cliente());

        return "registro/registro_form";
    }

    @PostMapping("/save_cliente")
    public String createCliente(Cliente cliente, Model model, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {

        clienteService.registroCliente(cliente);
        sendVerificationEmail(request, cliente);

        model.addAttribute("tituloPagina", "¡Registro efectuado con éxito!");

        return "registro/registro_success";
    }

    private void sendVerificationEmail(HttpServletRequest request, Cliente cliente)
            throws UnsupportedEncodingException, MessagingException {

        EmailUtil emailSettings = ajusteService.getAjustesEmail();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

        String toAddress = cliente.getEmail();
        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[nombre]]", cliente.getNombreCompleto());

        String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + cliente.getCodigoVerificacion();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("to Address: " + toAddress);
        System.out.println("Verify URL: " + verifyURL);
    }
}
