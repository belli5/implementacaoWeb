package com.exemple.implementacaoweb2.cliente;

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