package com.exemple.implementacaoweb2.cliente;

import java.util.List;

public interface ClienteRepository {

    Cliente save(Cliente cliente);

    void delete(int id);

    void update(int id);

    List<Cliente> findByNome(String nome);

    List<Cliente> findByEmail(String email);

    List<Cliente> findByTelefone(String telefone);

    List<Cliente> findByPrestadoresFavoritosId(int prestadorId);
}
