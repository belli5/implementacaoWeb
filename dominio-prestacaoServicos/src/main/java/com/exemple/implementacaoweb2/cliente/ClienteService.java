package com.exemple.implementacaoweb2.cliente;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    public final ClienteRepository clienteRepository;

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

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    public List<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefone(telefone);
    }

    public List<Cliente> buscarPorPrestadorFavorito(int prestadorId) {
        return clienteRepository.findByPrestadoresFavoritosId(prestadorId);
    }
}
