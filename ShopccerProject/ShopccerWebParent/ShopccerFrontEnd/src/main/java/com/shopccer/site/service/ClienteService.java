package com.shopccer.site.service;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {

    List<Pais> listAllPaises();

    boolean isEmailUnique(String email);

    void registroCliente(Cliente cliente);

    boolean verify(String codigoVerificacion);

    Cliente findClienteByEmail(String email);

    void update(Cliente clienteInForm);
}
