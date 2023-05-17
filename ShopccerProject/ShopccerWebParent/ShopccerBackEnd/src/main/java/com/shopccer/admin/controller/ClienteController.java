package com.shopccer.admin.controller;

import com.shopccer.admin.exception.ClienteNotFoundException;
import com.shopccer.admin.service.ClienteService;
import com.shopccer.admin.service.impl.ClienteServiceImpl;
import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "nombre", "asc", null);
    }

    @GetMapping("/clientes/pagina/{numeroPagina}")
    public String listByPage(Model model,
                             @PathVariable(name = "numeroPagina") int numeroPagina,
                             @Param("campoOrden") String campoOrden,
                             @Param("dirOrden") String dirOrden,
                             @Param("palabraClave") String palabraClave) {

        Page<Cliente> pagina = clienteService.listByPage(numeroPagina, campoOrden, dirOrden, palabraClave);
        List<Cliente> listaClientes = pagina.getContent();

        long startCount = (numeroPagina - 1) * ClienteServiceImpl.CLIENTES_POR_PAG + 1;
        long endCount = startCount + ClienteServiceImpl.CLIENTES_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        model.addAttribute("paginasTotales", pagina.getTotalPages());
        model.addAttribute("clientesTotales", pagina.getTotalElements());
        model.addAttribute("paginaActual", numeroPagina);
        model.addAttribute("listaClientes", listaClientes);
        model.addAttribute("campoOrden", campoOrden);
        model.addAttribute("dirOrden", dirOrden);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("dirOrdenContrario", dirOrden.equals("asc") ? "desc" : "asc");
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);

        return "clientes/clientes";
    }

    @GetMapping("/clientes/{idCliente}/activo/{bool}")
    public String updateClienteActivo(@PathVariable("idCliente") Integer idCliente,
                                              @PathVariable("bool") boolean activo, RedirectAttributes redirectAttributes) {
        clienteService.updateClienteEnabledStatus(idCliente, activo);
        String status = activo ? "activado" : "desactivado";
        String msg = "El cliente con id: " + idCliente + " ha sido " + status;
        redirectAttributes.addFlashAttribute("msg", msg);

        return "redirect:/clientes";
    }

    @GetMapping("/clientes/detalles/{idCliente}")
    public String viewCliente(@PathVariable("idCliente") Integer idCliente, Model model, RedirectAttributes ra) {
        try {
            Cliente cliente = clienteService.findById(idCliente);
            model.addAttribute("cliente", cliente);

            return "clientes/cliente_modal_detalle";
        } catch (ClienteNotFoundException ex) {
            ra.addFlashAttribute("msg", ex.getMessage());
            return "redirect:/clientes";
        }
    }

    @GetMapping("/clientes/edit/{idCliente}")
    public String editCliente(@PathVariable("idCliente") Integer idCliente, Model model, RedirectAttributes ra) {
        try {
            Cliente cliente = clienteService.findById(idCliente);
            List<Pais> paises = clienteService.listAllPaises();

            model.addAttribute("listaPaises", paises);
            model.addAttribute("cliente", cliente);
            model.addAttribute("tituloPagina", String.format("Editar Cliente (Id: %d)", idCliente));

            return "clientes/cliente_form";

        } catch (ClienteNotFoundException ex) {
            ra.addFlashAttribute("msg", ex.getMessage());
            return "redirect:/clientes";
        }
    }

    @PostMapping("/clientes/save")
    public String saveCliente(Cliente cliente, Model model, RedirectAttributes ra) {
        clienteService.save(cliente);
        ra.addFlashAttribute("msg", "El cliente con id: " + cliente.getIdCliente() + " ha sido guardado correctamente.");
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/delete/{idCliente}")
    public String deleteCliente(@PathVariable Integer idCliente, RedirectAttributes ra) {
        try {
            clienteService.deleteById(idCliente);
            ra.addFlashAttribute("msg", "El cliente con id: " + idCliente + " ha sido eliminado correctamente.");

        } catch (ClienteNotFoundException ex) {
            ra.addFlashAttribute("msg", ex.getMessage());
        }

        return "redirect:/clientes";
    }
}
