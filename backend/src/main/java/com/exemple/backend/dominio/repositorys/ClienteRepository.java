package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    public Optional<Cliente> findById(int id);
    public List<Cliente> findAll();
    public Cliente save(Cliente cliente);
    public Cliente update(Cliente cliente);
    public void delete(int id);
}
