package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.Pais;
import com.shopccer.site.repository.ClienteRepository;
import com.shopccer.site.repository.PaisRepository;
import com.shopccer.site.service.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Pais> listAllPaises() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public boolean isEmailUnique(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        return cliente == null;
    }
    @Override
    public void registroCliente(Cliente cliente) {
        encodePassword(cliente);
        cliente.setActivo(false);
        cliente.setCreatedTime(new Date());


        String randomCode = generateRandomCode(64);
        System.out.println(randomCode);
        cliente.setCodigoVerificacion(randomCode);

        clienteRepository.save(cliente);

    }

    @Override
    public boolean verify(String codigoVerificacion) {
            Cliente cliente = clienteRepository.findByCodigoVerificacion(codigoVerificacion);

            if (cliente == null || cliente.isActivo()) {
                return false;
            } else {
                clienteRepository.enable(cliente.getIdCliente());
                return true;
            }
    }

    private void encodePassword(Cliente cliente) {
        String encodedPassword = passwordEncoder.encode(cliente.getPassword());
        cliente.setPassword(encodedPassword);
    }

    private String generateRandomCode(Integer longitud){

        Integer length = longitud; // Longitud del string aleatorio
        String caracteresValidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            Integer index = random.nextInt(caracteresValidos.length());
            char randomChar = caracteresValidos.charAt(index);
            sb.append(randomChar);
        }

        String randomCode = sb.toString();
        System.out.println(randomCode);

        return randomCode;
    }
}
