package com.shopccer.admin.service;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService {

    Page<Pedido> listByPage(int numeroPagina, String campoOrden, String dirOrden, String palabraClave);
}
