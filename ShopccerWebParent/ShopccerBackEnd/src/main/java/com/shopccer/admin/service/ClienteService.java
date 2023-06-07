package com.shopccer.admin.service;

import com.shopccer.admin.exception.ClienteNotFoundException;
import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {

    Page<Cliente> listByPage(int numeroPagina, String campoOrden, String dirOrden, String palabraClave);
    void updateClienteEnabledStatus(Integer idCliente, boolean activo);

    Cliente findById(Integer idCliente) throws ClienteNotFoundException;

    List<Pais> listAllPaises();

    boolean isEmailUnique(Integer id, String email);

    void save(Cliente clienteInForm);

    void deleteById(Integer id) throws ClienteNotFoundException;
}
