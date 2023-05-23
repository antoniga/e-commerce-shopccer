package com.shopccer.site.restcontroller;

import com.shopccer.common.entity.Cliente;
import com.shopccer.site.exception.ClienteNotFoundException;
import com.shopccer.site.exception.ItemCarroException;
import com.shopccer.site.service.ClienteService;
import com.shopccer.site.service.ItemCarroService;
import com.shopccer.site.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemCarroRestController {

    @Autowired private ItemCarroService itemCarroService;
    @Autowired private ClienteService clienteService;

    @PostMapping("/carro/add/{idProducto}/{talla}/{cantidad}")
    public String addProductToCart(@PathVariable("idProducto") Integer productId,
                                   @PathVariable("talla") Integer talla,
                                   @PathVariable("cantidad") Integer cantidad,
                                   HttpServletRequest request) {

        try {
            Cliente cliente = getClienteAutenticado(request);
            Integer updatedQuantity = itemCarroService.addProduct(productId, talla,cantidad, cliente);

            return updatedQuantity + " item(s) del producto han sido añadidos al carro de la compra.";
        } catch (ClienteNotFoundException ex) {
            return "Debes loguearte para añadir productos a tu carro de la compra.";
        } catch (ItemCarroException ex) {
            return ex.getMessage();
        }

    }

    @PostMapping("/carro/update/{productId}/{talla}/{cantidad}/{variacionStock}")
    public String updateQuantity(@PathVariable("productId") Integer idProducto,
                                 @PathVariable("talla") Integer talla,
                                 @PathVariable("cantidad") Integer cantidad,
                                 @PathVariable("variacionStock") String variacionStock,
                                 HttpServletRequest request) {
        try {
            Cliente cliente = getClienteAutenticado(request);
            Double subtotal = itemCarroService.updateCantidad(idProducto, talla, cantidad, cliente, variacionStock);
            subtotal = Math.round((subtotal) * 100.0) / 100.0;
            return String.valueOf(subtotal);
        } catch (ClienteNotFoundException ex) {
            return "Debes loguearte para actualizar la cantidad del producto.";
        }
    }

    @DeleteMapping("/carro/remove/{idProducto}/{talla}")
    public String removeProduct(@PathVariable("idProducto") Integer idProducto,
                                @PathVariable("talla") String talla,
                                HttpServletRequest request) {
        try {
            Cliente cliente = getClienteAutenticado(request);
            itemCarroService.removeProducto(cliente,idProducto,talla);

            return "El producto ha sido eliminado de su cesta de la compra.";

        } catch (ClienteNotFoundException e) {
            return "Debes loguearte para eliminar el producto.";
        }
    }

    private Cliente getClienteAutenticado(HttpServletRequest request)
            throws ClienteNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new ClienteNotFoundException("Cliente no autenticado");
        }

        return clienteService.findClienteByEmail(email);
    }
}
