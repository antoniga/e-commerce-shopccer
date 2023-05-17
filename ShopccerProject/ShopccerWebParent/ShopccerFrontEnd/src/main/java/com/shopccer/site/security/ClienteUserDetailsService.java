package com.shopccer.site.security;

import com.shopccer.common.entity.Cliente;
import com.shopccer.site.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClienteUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null)
            throw new UsernameNotFoundException("Ningún cliente encontrado con el email: " + email);

        return new ClienteUserDetails(cliente);
    }
}
