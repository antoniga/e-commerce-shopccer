package com.shopccer.admin.controller;

import com.shopccer.admin.service.AjusteService;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.admin.utils.GeneralUtil;
import com.shopccer.common.entity.Ajuste;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class AjusteController {

    @Autowired
    private AjusteService ajusteService;

    @GetMapping("/ajustes")
    public String listAll(Model model){

        List<Ajuste> listaAjustes = ajusteService.listAllAjustes();

        listaAjustes.forEach(ajuste -> model.addAttribute(ajuste.getClave(), ajuste.getValor()));

        return "ajustes/ajustes";
    }

    @PostMapping("/ajustes/save_general")
    public String saveAjustesGenerales(@RequestParam("logoImg")MultipartFile multipartFile, HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) throws IOException {

        GeneralUtil ajustesGenerales = ajusteService.getAjustesGenerales();

        saveSiteLogo(multipartFile, ajustesGenerales);
        System.out.println(ajustesGenerales.get("SITE_LOGO"));
        updateAjustesGeneralesEnBBDD(request,ajustesGenerales.list());

        redirectAttributes.addFlashAttribute("msg","Los ajustes generales han sido guardados.");

        return "redirect:/ajustes";
    }

    @PostMapping("/ajustes/save_plantillas_correo")
    public String saveMailTemplatesSetttings(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        List<Ajuste> ajustesMailTemplates = ajusteService.getAjustesMailTemplates();
        updateAjustesGeneralesEnBBDD(request, ajustesMailTemplates);

        redirectAttributes.addFlashAttribute("msg", "Los ajustes de las plantillas de correo " +
                "han sido guardados");

        return "redirect:/ajustes#servidor";
    }

    @PostMapping("/ajustes/save_mail_server")
    public String saveMailServerSetttings(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        List<Ajuste> ajustesMailServer = ajusteService.getAjustesMailServer();
        updateAjustesGeneralesEnBBDD(request, ajustesMailServer);

        redirectAttributes.addFlashAttribute("msg", "Los ajustes del servidor de correo han sido " +
                "guardados");

        return "redirect:/ajustes#servidor";
    }

    @PostMapping("/ajustes/save_pago")
    public String saveAjustesPago(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        List<Ajuste> ajustesPago = ajusteService.getAjustesPagos();
        updateAjustesGeneralesEnBBDD(request, ajustesPago);

        redirectAttributes.addFlashAttribute("msg", "Los ajustes de pago han sido " +
                "guardados");

        return "redirect:/ajustes#pagos";
    }

    private static void saveSiteLogo(MultipartFile multipartFile, GeneralUtil ajustesGenerales) throws IOException {
        if(!multipartFile.isEmpty()) {
            String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String valor = "/site-logo/"+nombreArchivo;
            //ajustesGenerales.updateSiteLogo(valor);
            List<Ajuste> listaAjustes = ajustesGenerales.list();
            listaAjustes.stream()
                    .filter(ajuste -> ajuste.getClave().equals("SITE_LOGO"))
                    .findFirst().orElse(null).setValor(valor);
            String dirSubida="../site-logo/";
            FileLoadUtil.cleanDir(dirSubida);
            FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
        }
    }

    private void updateAjustesGeneralesEnBBDD(HttpServletRequest request, List<Ajuste> listaAjustes){
        listaAjustes.forEach(ajuste -> System.out.println(ajuste.getClave()+" -- "+ajuste.getValor()));
        for(Ajuste ajuste : listaAjustes){
            String valor = request.getParameter(ajuste.getClave());
            if(valor !=null){
                ajuste.setValor(valor);
            }
        }
        ajusteService.saveAll(listaAjustes);
    }

}
