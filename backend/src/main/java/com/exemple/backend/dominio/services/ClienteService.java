package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.repositorys.ClienteRepository;
import com.exemple.backend.dominio.strategies.ClienteValidadorStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteValidadorStrategy validador;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteValidadorStrategy validador) {
        this.clienteRepository = clienteRepository;
        this.validador = validador;
    }

    public Optional<Cliente> findById(int id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente create(Cliente cliente) {
        validador.validar(cliente);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(Cliente cliente) {
        clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + cliente.getId()));
        validador.validar(cliente);
        return clienteRepository.update(cliente);
    }

    @Transactional
    public void delete(int id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
        clienteRepository.delete(id);
    }
}
