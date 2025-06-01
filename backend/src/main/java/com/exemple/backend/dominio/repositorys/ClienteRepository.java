package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
     Optional<Cliente> findById(int id);
     List<Cliente> findAll();
     Cliente save(Cliente cliente);
     Cliente update(Cliente cliente);
     void delete(int id);
}
