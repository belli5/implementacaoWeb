package com.seuservico.infraestrutura.persistencia.jpa.clientejpa;

import com.exemple.implementacaoweb2.cliente.Cliente;
import com.exemple.implementacaoweb2.cliente.ClienteRepository;
import com.seuservico.infraestrutura.persistencia.jpa.MapperGeral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClienteJpaRepositoryImpl implements ClienteRepository {

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @Override
    public Cliente save(Cliente cliente) {
        ClienteJpa clienteJpa = MapperGeral.toClienteJpa(cliente);
        ClienteJpa saved = clienteJpaRepository.save(clienteJpa);
        return MapperGeral.toCliente(saved);
    }

    @Override
    public void delete(int id) {
        clienteJpaRepository.deleteById(id);
    }

    @Override
    public void update(int id) {
        ClienteJpa clienteJpa = clienteJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Exemplo de atualização manual
        clienteJpa.setTelefone("Novo telefone atualizado");

        clienteJpaRepository.save(clienteJpa);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return clienteJpaRepository.findByNome(nome)
                .stream()
                .map(MapperGeral::toCliente)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cliente> findByEmail(String email) {
        return clienteJpaRepository.findByEmail(email)
                .stream()
                .map(MapperGeral::toCliente)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cliente> findByTelefone(String telefone) {
        return clienteJpaRepository.findByTelefone(telefone)
                .stream()
                .map(MapperGeral::toCliente)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cliente> findByPrestadoresFavoritosId(int prestadorId) {
        return clienteJpaRepository.findByPrestadoresFavoritos_Id(prestadorId)
                .stream()
                .map(MapperGeral::toCliente)
                .collect(Collectors.toList());
    }
}
