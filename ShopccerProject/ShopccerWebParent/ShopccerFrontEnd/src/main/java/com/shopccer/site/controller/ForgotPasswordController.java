package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.site.exception.ClienteNotFoundException;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private AjusteService ajusteService;

    @GetMapping("/forgot_password")
    public String showRequestForm() {
        return "clientes/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processRequestForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        try {
            String token = clienteService.updateResetPasswordToken(email);
            String link = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(link, email);

            model.addAttribute("message", "Le hemos enviado un link para resetear la " +
                    "contraseña a su email. Compruebe su correo, por favor.");
        } catch (ClienteNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "No se pudo enviar el email.");
        }

        return "clientes/forgot_password_form";
    }

    private void sendEmail(String link, String email)
            throws UnsupportedEncodingException, MessagingException {
        EmailUtil ajustesEmail = ajusteService.getAjustesEmail();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(ajustesEmail);

        String toAddress = email;
        String subject = "Here's the link to reset your password";

        String content = "<p>Hola,</p>"
                + "<p>Has solicitado restablecer tu contraseña.</p>"
                + "Haz clic en el enlace de abajo para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + link + "\">Cambiar mi contraseña</a></p>"
                + "<br>"
                + "<p>Ignora este correo electrónico si recuerdas tu contraseña, "
                + "o si no has hecho la solicitud.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(ajustesEmail.getFromAddress(), ajustesEmail.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetForm(@Param("token") String token, Model model) {
        Cliente cliente = clienteService.getByResetPasswordToken(token);
        if (cliente != null) {
            model.addAttribute("token", token);
        } else {
            model.addAttribute("tituloPagina", "Token Inválido");
            model.addAttribute("msg", "Token Inválido");
            return "mensaje";
        }

        return "clientes/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        try {
            clienteService.updatePassword(token, password);

            model.addAttribute("pageTitle", "Reset Your Password");
            model.addAttribute("title", "Reset Your Password");
            model.addAttribute("message", "You have successfully changed your password.");

        } catch (ClienteNotFoundException e) {
            model.addAttribute("tituloPagina", "Token Inválido");
            model.addAttribute("msg", e.getMessage());
        }

        return "mensaje";
    }
}
