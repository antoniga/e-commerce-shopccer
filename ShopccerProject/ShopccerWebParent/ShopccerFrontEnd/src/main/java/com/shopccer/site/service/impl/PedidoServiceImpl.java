package com.shopccer.site.service.impl;

import com.shopccer.common.entity.*;
import com.shopccer.site.checkout.CheckoutInfo;
import com.shopccer.site.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PedidoServiceImpl implements com.shopccer.site.service.PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido createPedido(Cliente cliente, List<ItemCarro> itemsCarro, CheckoutInfo checkoutInfo) {
        Pedido pedido = new Pedido();

        pedido.setFechaPedido(new Date());
        pedido.setEstado(EstadoPedido.PAGADO);
        pedido.setCliente(cliente);
        pedido.setCosteProducto(checkoutInfo.getCosteProducto());
        pedido.setTotal(checkoutInfo.getTotalProducto());
        pedido.setCosteEnvio(checkoutInfo.getCosteEnvio());
        pedido.setTotal(checkoutInfo.getTotalPago());
        pedido.setDiasEntrega(checkoutInfo.getDiasEntrega());
        pedido.setFechaEntrega(checkoutInfo.getFechaEntrega());

        copiarDireccionDeCliente(pedido,cliente);


        Set<DetallePedido> detallesPedido = pedido.getDetallesPedido();

        for (ItemCarro item : itemsCarro) {
            Producto producto = item.getProducto();

            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setProducto(producto);
            detallePedido.setCantidad(item.getCantidad());
            detallePedido.setPrecioUnitario(producto.getPrecioConDescuento());
            detallePedido.setCosteProducto(producto.getPrecio() * item.getCantidad());
            detallePedido.setSubtotal(item.getSubtotal());

            detallesPedido.add(detallePedido);
        }


        return pedidoRepository.save(pedido);
    }

    private void copiarDireccionDeCliente(Pedido pedido, Cliente cliente) {
        pedido.setNombre(cliente.getNombre());
        pedido.setApellidos(cliente.getApellidos());
        pedido.setNumeroTelefono(cliente.getNumeroTelefono());
        pedido.setDireccion(cliente.getDireccion());
        pedido.setCiudad(cliente.getCiudad());
        pedido.setComunidad(cliente.getComunidad());
        pedido.setPais(cliente.getPais().getNombre());
        pedido.setCodPostal(cliente.getCodPostal());
    }
}
