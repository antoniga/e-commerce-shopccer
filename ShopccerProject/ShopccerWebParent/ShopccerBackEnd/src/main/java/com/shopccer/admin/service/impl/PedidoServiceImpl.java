package com.shopccer.admin.service.impl;

import com.shopccer.admin.repository.PedidoRepository;
import com.shopccer.admin.service.PedidoService;
import com.shopccer.common.entity.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    public static final int PEDIDOS_POR_PAG = 10;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public Page<Pedido> listByPage(int numeroPagina, String campoOrden, String dirOrden, String palabraClave) {

        Sort sort = null;

        if ("destino".equals(campoOrden)) {
            sort = Sort.by("pais").and(Sort.by("comunidad")).and(Sort.by("ciudad"));
        } else {
            sort = Sort.by(campoOrden);
        }

        sort = ("asc").equals(dirOrden) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(numeroPagina - 1, PEDIDOS_POR_PAG, sort);

        if (palabraClave != null) {
            return pedidoRepository.findAll(palabraClave, pageable);
        }

        return pedidoRepository.findAll(pageable);
    }
}
