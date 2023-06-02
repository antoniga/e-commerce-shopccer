package com.shopccer.site.service.impl;

import com.shopccer.common.entity.*;
import com.shopccer.site.checkout.CheckoutInfo;
import com.shopccer.site.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PedidoServiceImpl implements com.shopccer.site.service.PedidoService {

    private static final int PEDIDOS_POR_PAG = 5;
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

        SeguimientoPedido trackNuevo = new SeguimientoPedido();
        trackNuevo.setPedido(pedido);
        trackNuevo.setEstado(EstadoPedido.NUEVO);
        trackNuevo.setNotas(EstadoPedido.NUEVO.defaultDescription());
        trackNuevo.setUpdatedTime(new Date());

        SeguimientoPedido trackPagado = new SeguimientoPedido();
        trackPagado.setPedido(pedido);
        trackPagado.setEstado(EstadoPedido.PAGADO);
        trackPagado.setNotas(EstadoPedido.PAGADO.defaultDescription());
        trackPagado.setUpdatedTime(new Date());

        pedido.getSeguimientosPedido().add(trackNuevo);
        pedido.getSeguimientosPedido().add(trackPagado);


        return pedidoRepository.save(pedido);
    }

    @Override
    public Page<Pedido> listarClientesByPage(Cliente cliente, int numeroPagina, String campoOrden, String dirOrden, String palabraClave) {

            Sort sort = Sort.by(campoOrden);
            sort = dirOrden.equals("asc") ? sort.ascending() : sort.descending();

            Pageable pageable = PageRequest.of(numeroPagina - 1, PEDIDOS_POR_PAG, sort);

            if (palabraClave != null) {
                return pedidoRepository.findAll(palabraClave, cliente.getIdCliente(), pageable);
            }

            return pedidoRepository.findAll(cliente.getIdCliente(), pageable);
    }

    @Override
    public Pedido findByIdAndCliente(Integer idCliente, Cliente cliente) {
        return pedidoRepository.findByIdPedidoAndCliente(idCliente, cliente);
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
