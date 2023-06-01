package com.shopccer.admin.controller;

import com.shopccer.admin.exception.PedidoNotFoundException;
import com.shopccer.admin.repository.PaisRepository;
import com.shopccer.admin.service.PedidoService;
import com.shopccer.admin.service.impl.PedidoServiceImpl;
import com.shopccer.common.entity.EstadoPedido;
import com.shopccer.common.entity.Pais;
import com.shopccer.common.entity.Pedido;
import com.shopccer.common.entity.SeguimientoPedido;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PaisRepository paisRepository;

    @GetMapping("/pedidos")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "fechaPedido", "desc", null);
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

    @GetMapping("/pedidos/delete/{idPedido}")
    public String deleteOrder(@PathVariable("idPedido") Integer idPedido, Model model, RedirectAttributes redirectAttributes) {
        try {
            pedidoService.deleteById(idPedido);;
            redirectAttributes.addFlashAttribute("msg", "El pedido con id: " + idPedido + " ha sido eliminado.");
        } catch (PedidoNotFoundException ex) {
            redirectAttributes.addFlashAttribute("msg", ex.getMessage());
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/pedidos/edit/{idPedido}")
    public String editOrder(@PathVariable("idPedido") Integer idPedido, Model model, RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {
        try {
            Pedido pedido = pedidoService.findById(idPedido);;

            List<Pais> listaPaises = paisRepository.findAllByOrderByNombreAsc();

            model.addAttribute("tituloPagina", "Editar pedido (ID: " + idPedido + ")");
            model.addAttribute("pedido", pedido);
            model.addAttribute("listaPaises", listaPaises);

            return "pedidos/pedidos_form";

        } catch (PedidoNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/pedidos";
        }

    }

    @PostMapping("/pedidos/save")
    public String saveOrder(Pedido pedido, HttpServletRequest request, RedirectAttributes ra) throws PedidoNotFoundException {

        Integer idPedido = Integer.valueOf(request.getParameter("idPedido"));
        Pedido pedidoInDB= pedidoService.findById(idPedido);
        updateOrderTracks(pedidoInDB, request);

        List<SeguimientoPedido> listaOrdenada = pedidoInDB.getSeguimientosPedido().stream()
                .sorted(Comparator.comparing(SeguimientoPedido::getUpdatedTime).reversed())
                .collect(Collectors.toList());

        EstadoPedido estado = listaOrdenada.get(0).getEstado();
        pedidoInDB.setEstado(estado);
        pedidoService.save(pedidoInDB);

        ra.addFlashAttribute("msg", "El pedido con id: " + pedido.getIdPedido() +
                " ha sido actualizado correctamente.");

        return "redirect:/pedidos";
    }


    private void updateOrderTracks(Pedido pedido, HttpServletRequest request) {
        String[] trackIds = request.getParameterValues("trackId");
        String[] trackStatuses = request.getParameterValues("trackStatus");
        String[] trackDates = request.getParameterValues("trackDate");
        String[] trackNotes = request.getParameterValues("trackNotes");

        List<SeguimientoPedido> orderTracks = pedido.getSeguimientosPedido();
        orderTracks.clear();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (int i = 0; i < trackIds.length; i++) {
            SeguimientoPedido trackRecord = new SeguimientoPedido();

            Integer trackId = Integer.parseInt(trackIds[i]);
            if (trackId > 0) {
                trackRecord.setIdSeguimientoPedido(trackId);
            }

            trackRecord.setPedido(pedido);
            trackRecord.setEstado(EstadoPedido.valueOf(trackStatuses[i]));
            trackRecord.setNotas(trackNotes[i]);

            try {
                trackRecord.setUpdatedTime(dateFormatter.parse(trackDates[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderTracks.add(trackRecord);
        }
    }


}
