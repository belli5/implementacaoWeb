package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.repositorys.ClienteRepository;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.mappers.ClienteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository) {
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Optional<Cliente> findById(int id) {
        return clienteJpaRepository.findById(id)
                .map(ClienteMapper::toCliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteJpaRepository.findAll()
                .stream()
                .map(ClienteMapper::toCliente)
                .toList();
    }

    @Override
    public Cliente save(Cliente cliente) {
        notNull(cliente, "Cliente não deve ser nulo");

        ClienteJpa entity = ClienteMapper.toClienteJpa(cliente);
        ClienteJpa saved = clienteJpaRepository.save(entity);
        return ClienteMapper.toCliente(saved);
    }

    @Override
    public Cliente update(Cliente cliente) {
        // Mesmo processo do save, pois o JPA faz o merge quando o ID já existe
        ClienteJpa entity = ClienteMapper.toClienteJpa(cliente);
        ClienteJpa updated = clienteJpaRepository.save(entity);
        return ClienteMapper.toCliente(updated);
    }

    @Override
    public void delete(int id) {
        clienteJpaRepository.deleteById(id);
    }
}
