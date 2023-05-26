package com.shopccer.site.service;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Pedido;
import com.shopccer.site.checkout.CheckoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PedidoService {

    Pedido createPedido(Cliente cliente, List<ItemCarro> itemsCarro, CheckoutInfo checkoutInfo);
}
