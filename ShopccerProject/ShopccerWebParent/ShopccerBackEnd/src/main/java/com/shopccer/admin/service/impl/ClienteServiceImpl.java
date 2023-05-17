package com.shopccer.admin.service.impl;

import com.shopccer.admin.exception.ClienteNotFoundException;
import com.shopccer.admin.repository.ClienteRepository;
import com.shopccer.admin.repository.PaisRepository;
import com.shopccer.admin.service.ClienteService;
import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    public static final int CLIENTES_POR_PAG = 10;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Cliente> listByPage(int numeroPagina, String campoOrden, String dirOrden, String palabraClave) {
        Sort sort = Sort.by(campoOrden);
        sort = ("asc").equals(dirOrden) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(numeroPagina - 1, CLIENTES_POR_PAG, sort);

        if (palabraClave != null) {
            return clienteRepository.findAll(palabraClave, pageable);
        }

        return clienteRepository.findAll(pageable);
    }

    public void updateClienteEnabledStatus(Integer idCliente, boolean activo) {
        clienteRepository.updateEnabledStatus(idCliente, activo);
    }

    public Cliente findById(Integer idCliente) throws ClienteNotFoundException {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNotFoundException("No existe ningún cliente con id: " + idCliente));
    }

    public List<Pais> listAllPaises() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

    public boolean isEmailUnique(Integer id, String email) {
        Cliente clienteEmail = clienteRepository.findByEmail(email);

        if (clienteEmail != null && clienteEmail.getIdCliente() != id) {
            // encontrado otro cliente con el mismo correo electrónico
            return false;
        }

        return true;
    }

    public void save(Cliente clienteInForm) {
        Cliente clienteInBBDD = clienteRepository.findById(clienteInForm.getIdCliente()).get();
        if (!clienteInForm.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(clienteInForm.getPassword());
            clienteInForm.setPassword(encodedPassword);
        } else {
            clienteInForm.setPassword(clienteInBBDD.getPassword());
        }

        clienteInForm.setActivo(clienteInBBDD.isActivo());
        clienteInForm.setCreatedTime(clienteInBBDD.getCreatedTime());
        clienteInForm.setCodigoVerificacion(clienteInBBDD.getCodigoVerificacion());

        clienteRepository.save(clienteInForm);
    }

    public void deleteById(Integer id) throws ClienteNotFoundException {
        Long count = clienteRepository.countByIdCliente(id);
        if (count == null || count == 0) {
            throw new ClienteNotFoundException("No se ha encontrado ningún cliente con id: " + id);
        }

        clienteRepository.deleteById(id);
    }
}
