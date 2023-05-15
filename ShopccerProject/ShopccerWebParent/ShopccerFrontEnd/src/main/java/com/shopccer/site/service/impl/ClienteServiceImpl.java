package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import com.shopccer.site.repository.ClienteRepository;
import com.shopccer.site.repository.PaisRepository;
import com.shopccer.site.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PaisRepository paisRepository;

    @Override
    public List<Pais> listAllPaises() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public boolean isEmailUnique(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        return cliente == null;
    }
}
