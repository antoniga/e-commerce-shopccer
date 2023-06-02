package com.shopccer.site.controller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pedido;
import com.shopccer.site.service.ClienteService;
import com.shopccer.site.service.PedidoService;
import com.shopccer.site.service.impl.ProductoServiceImpl;
import com.shopccer.site.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired private PedidoService pedidoService;
    @Autowired private ClienteService clienteService;

    @GetMapping("/pedidos")
    public String listFirstPage(Model model, HttpServletRequest request) {
        return listPedidosByPage(model, request, 1, "fechaPedido", "desc", null);
    }

    @GetMapping("/pedidos/pagina/{numeroPagina}")
    public String listPedidosByPage(Model model, HttpServletRequest request,
                                    @PathVariable(name = "numeroPagina") int numeroPagina,
                                    String campoOrden, String dirOrden, String pedidoPalabraClave){
        Cliente cliente = getClienteAutenticado(request);

        Page<Pedido> pagina = pedidoService.listarClientesByPage(cliente, numeroPagina, campoOrden, dirOrden, pedidoPalabraClave);
        List<Pedido> listaPedidos = pagina.getContent();

        long startCount = (numeroPagina -1) * ProductoServiceImpl.PRODUCTOS_POR_PAG + 1;
        long endCount = startCount + ProductoServiceImpl.PRODUCTOS_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        String dirOrdenContrario = ("asc").equals(dirOrden) ? "desc" : "asc";

        model.addAttribute("paginaActual",numeroPagina);
        model.addAttribute("paginasTotales",pagina.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("pedidosTotales",pagina.getTotalElements());
        model.addAttribute("listaPedidos", listaPedidos);
        model.addAttribute("campoOrden", campoOrden);
        model.addAttribute("dirOrden", dirOrden);
        model.addAttribute("pedidoPalabraClave", pedidoPalabraClave);
        model.addAttribute("moduleURL", "/pedidos");
        model.addAttribute("dirOrdenContrario",dirOrdenContrario);

        return "pedidos/cliente_pedidos";
    }

    @GetMapping("/pedidos/detalles/{idPedido}")
    public String verDetallesPedido(Model model,@PathVariable(name = "idPedido") Integer idPedido, HttpServletRequest request) {
        Cliente cliente = getClienteAutenticado(request);

        Pedido pedido = pedidoService.findByIdAndCliente(idPedido, cliente);
        model.addAttribute("pedido", pedido);

        return "pedidos/pedido_detalle_modal";
    }

    private Cliente getClienteAutenticado(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return clienteService.findClienteByEmail(email);
    }
}
