package com.shopccer.admin.controller;

import com.shopccer.admin.exception.PedidoNotFoundException;
import com.shopccer.admin.service.PedidoService;
import com.shopccer.admin.service.impl.PedidoServiceImpl;
import com.shopccer.common.entity.Pedido;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "fechaPedido", "asc", null);
    }

    @GetMapping("/pedidos/pagina/{numeroPagina}")
    public String listByPage(Model model,
                             @PathVariable(name = "numeroPagina") int numeroPagina,
                             @RequestParam("campoOrden") String campoOrden,
                             @RequestParam("dirOrden") String dirOrden,
                             String palabraClave) {

        Page<Pedido> pagina = pedidoService.listByPage(numeroPagina, campoOrden, dirOrden, palabraClave);
        List<Pedido> listaPedidos = pagina.getContent();

        long startCount = (numeroPagina - 1) * PedidoServiceImpl.PEDIDOS_POR_PAG + 1;
        long endCount = startCount + PedidoServiceImpl.PEDIDOS_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        model.addAttribute("paginasTotales", pagina.getTotalPages());
        model.addAttribute("pedidosTotales", pagina.getTotalElements());
        model.addAttribute("paginaActual", numeroPagina);
        model.addAttribute("listaPedidos", listaPedidos);
        model.addAttribute("campoOrden", campoOrden);
        model.addAttribute("dirOrden", dirOrden);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("dirOrdenContrario", dirOrden.equals("asc") ? "desc" : "asc");
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);

        return "pedidos/pedidos";
    }

    @GetMapping("/pedidos/detalles/{idPedido}")
    public String viewOrderDetails(@PathVariable("idPedido") Integer idPedido, Model model,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Pedido pedido = pedidoService.findById(idPedido);
            model.addAttribute("pedido", pedido);

            return "pedidos/pedido_modal_detalle";
        } catch (PedidoNotFoundException ex) {
            redirectAttributes.addFlashAttribute("msg", ex.getMessage());
            return "redirect:/pedidos";
        }

    }
}
