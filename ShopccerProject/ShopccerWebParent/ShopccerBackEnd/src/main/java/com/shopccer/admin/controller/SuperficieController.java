package com.shopccer.admin.controller;

import com.shopccer.admin.exception.SuperficieNotFoundException;
import com.shopccer.admin.service.SuperficieService;
import com.shopccer.admin.service.impl.SuperficieServiceImpl;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.common.entity.Superficie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class SuperficieController {
    
    @Autowired
    private SuperficieService superficieService;
    
    @GetMapping("/superficies")
    public String listFirstPage(Model model){

        return listByPage(1, model, "idSuperficie", "asc", null);
    }
    
    @GetMapping("/superficies/pagina/{numeroPagina}")
    public String listByPage(@PathVariable(name = "numeroPagina") Integer numeroPagina, Model model,
                             @RequestParam("campoOrden") String campoOrden, @RequestParam("dirOrden") String dirOrden,
                             String palabraClave){

        Page<Superficie> pagina = superficieService.listByPage(numeroPagina, campoOrden, dirOrden, palabraClave);
        List<Superficie> listSuperficies = pagina.getContent();


        long startCount = (numeroPagina -1) * SuperficieServiceImpl.SUP_POR_PAG + 1;
        long endCount = startCount + SuperficieServiceImpl.SUP_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        String dirOrdenContrario = ("asc").equals(dirOrden) ? "desc" : "asc";

        model.addAttribute("listaSuperficies",listSuperficies);
        model.addAttribute("paginaActual", numeroPagina);
        model.addAttribute("paginasTotales",pagina.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("superficiesTotales",pagina.getTotalElements());
        model.addAttribute("campoOrden",campoOrden);
        model.addAttribute("dirOrden",dirOrden);
        model.addAttribute("dirOrdenContrario",dirOrdenContrario);
        model.addAttribute("palabraClave",palabraClave);

        return "superficies/superficies";
    }

    @GetMapping("/superficies/nuevo")
    public String addSuperficie(Model model){

        Superficie superficie = new Superficie();
        superficie.setActivo(true);
        model.addAttribute("superficie", superficie);
        model.addAttribute("tituloPagina","Crear nueva superficie");

        return "superficies/superficie_form";
    }

    @PostMapping("/superficies/save")
    public String saveSuperficie(Superficie superficie, @RequestParam("superficieImg")MultipartFile multipartFile,
                                 RedirectAttributes redirectAttributes) throws IOException{

        if(!multipartFile.isEmpty()){

            String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            superficie.setFoto(nombreArchivo);

            Superficie savedSuperficie = superficieService.save(superficie);

            String dirSubida = "../fotos-superficies/" + savedSuperficie.getIdSuperficie();
            FileLoadUtil.cleanDir(dirSubida);
            FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
        } else{

            if(superficie.getFoto().isEmpty()){
                superficie.setFoto(null);
            }

            superficieService.save(superficie);
        }

        redirectAttributes.addFlashAttribute("msg", "La superficie ha sido añadida correctamente");

        return getRedirectUrlToAffectedSuperficie(superficie);
    }

    @GetMapping("/superficies/edit/{idSuperficie}")
    public String editSuperficie(@PathVariable(name = "idSuperficie") Integer idSuperficie, Model model,
                                 RedirectAttributes redirectAttributes){

        try {

            Superficie superficie = superficieService.findById(idSuperficie);
            model.addAttribute("superficie", superficie);
            model.addAttribute("tituloPagina","Editar superficie (Id: "+idSuperficie+") ");

            return "superficies/superficie_form";
        } catch (SuperficieNotFoundException e){

            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/superficies";


        }
    }

    @GetMapping("/superficies/delete/{idSuperficie}")
    public String deleteSuperficie(@PathVariable(name = "idSuperficie") Integer idSuperficie, Model model,
                              RedirectAttributes redirectAttributes) {

        try {
            superficieService.deleteById(idSuperficie);
            redirectAttributes.addFlashAttribute("msg", "La superficie con id: " + idSuperficie + " ha sido eliminada.");
        } catch (SuperficieNotFoundException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:/superficies";
    }
    @GetMapping("/superficies/{idSuperficie}/activo/{bool}")
    public String updateSuperficieActivo(@PathVariable(name = "idSuperficie") Integer idSuperficie,
                                    @PathVariable(name = "bool") Boolean activo, RedirectAttributes redirectAttributes) {

        superficieService.updateSuperficieActiva(idSuperficie, activo);
        String msgActivo = activo ? "activado" : "desactivado";
        String msg = "La superficie con id: " + idSuperficie + " ha sido " + msgActivo;

        redirectAttributes.addFlashAttribute("msg", msg);

        return "redirect:/superficies";
    }
    private String getRedirectUrlToAffectedSuperficie(Superficie superficie) {

        String nombre = superficie.getNombre();
        return "redirect:/superficies/pagina/1?campoOrden=idSuperficie&dirOrden=asc&palabraClave=" + nombre;
    }
}
