package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import com.shopccer.site.security.ClienteUserDetails;
import com.shopccer.site.service.AjusteService;
import com.shopccer.site.service.ClienteService;
import com.shopccer.site.util.EmailUtil;
import com.shopccer.site.util.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/verify")
    public String verifyCuenta(@Param("code") String code, Model model) {
        boolean verificado = clienteService.verify(code);

        return "registro/" + (verificado ? "verificado_success" : "verificado_fail");
    }


    @GetMapping("/detalles_cuenta")
    public String verDetallesCuentas(Model model, HttpServletRequest request) {
        String email = getEmailOfAuthenticatedCustomer(request);
        Cliente cliente = clienteService.findClienteByEmail(email);
        List<Pais> listaPaises = clienteService.listAllPaises();

        model.addAttribute("cliente", cliente);
        model.addAttribute("listaPaises", listaPaises);

        return "clientes/cuenta_form";
    }

    private String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
        Object principal = request.getUserPrincipal();
        String clienteEmail = null;

        if (principal instanceof UsernamePasswordAuthenticationToken
                || principal instanceof RememberMeAuthenticationToken) {
            clienteEmail = request.getUserPrincipal().getName();
        }
        return clienteEmail;
    }

    @PostMapping("/update_detalles_cuenta")
    public String updateAccountDetails(Model model, Cliente cliente, RedirectAttributes ra,
                                       HttpServletRequest request) {
        clienteService.update(cliente);
        ra.addFlashAttribute("msg", "Los detalles de tu cuenta han sido actualizados. " +
                "Por favor, reinicia sesión para ver los cambios.");


        return "redirect:/detalles_cuenta";
    }

}
