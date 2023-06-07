package com.shopccer.site.restcontroller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.site.dto.PedidoReturnRequest;
import com.shopccer.site.dto.PedidoReturnResponse;
import com.shopccer.site.exception.ClienteNotFoundException;
import com.shopccer.site.exception.PedidoNotFoundException;
import com.shopccer.site.service.ClienteService;
import com.shopccer.site.service.PedidoService;
import com.shopccer.site.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoRestController {


    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/pedidos/return")
    public ResponseEntity<?> handleOrderReturnRequest(@RequestBody PedidoReturnRequest returnRequest,
                                                      HttpServletRequest servletRequest) {

        System.out.println("Id pedido: " + returnRequest.getIdPedido());
        System.out.println("Razon: " + returnRequest.getRazon());
        System.out.println("Notas: " + returnRequest.getNotas());

        Cliente cliente = null;

        try {
            cliente = getClienteAutenticado(servletRequest);
        } catch (ClienteNotFoundException ex) {
            return new ResponseEntity<>("Autenticación requerida", HttpStatus.BAD_REQUEST);
        }

        try {
            pedidoService.setPedidoReturnRequested(returnRequest, cliente);
        } catch (PedidoNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new PedidoReturnResponse(returnRequest.getIdPedido()), HttpStatus.OK);
    }

    private Cliente getClienteAutenticado(HttpServletRequest request)
            throws ClienteNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new ClienteNotFoundException("El cliente no se autenticó.");
        }

        return clienteService.findClienteByEmail(email);
    }
}
