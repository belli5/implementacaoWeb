package org.example.service.usuario;


import org.example.model.usuario.Cliente;
import org.example.persistence.repository.usuarios.ClienteRepository;

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