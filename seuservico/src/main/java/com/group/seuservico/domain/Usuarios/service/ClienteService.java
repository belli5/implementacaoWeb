package com.group.seuservico.domain.Usuarios.service;

import com.group.seuservico.domain.Usuarios.model.Cliente;
import com.group.seuservico.domain.Usuarios.repository.ClienteRepository;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deletarCliente(int id) {
        clienteRepository.delete(id);
    }

    public void atualizarCliente(int id) {
        clienteRepository.update(id);
    }
}