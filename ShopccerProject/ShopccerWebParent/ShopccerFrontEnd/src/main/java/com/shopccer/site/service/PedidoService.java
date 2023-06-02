package com.shopccer.site.service;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Pedido;
import com.shopccer.site.checkout.CheckoutInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PedidoService {

    Pedido createPedido(Cliente cliente, List<ItemCarro> itemsCarro, CheckoutInfo checkoutInfo);

    Page<Pedido> listarClientesByPage(Cliente cliente, int numeroPagina, String campoOrden, String dirOrden, String palabraClave);


    Pedido findByIdAndCliente(Integer idCliente, Cliente cliente);
}
