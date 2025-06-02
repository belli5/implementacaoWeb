package com.exemple.backend.infraestrutura.security;

import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class ClienteUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteJpaRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ClienteJpa cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado: " + email));

        return User.builder()
                .username(cliente.getEmail())
                .password(cliente.getSenha())
                .roles("USER")
                .build();
    }
}
