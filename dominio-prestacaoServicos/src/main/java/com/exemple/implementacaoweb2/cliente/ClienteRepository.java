package com.exemple.implementacaoweb2.cliente;

import java.util.List;

public interface ClienteRepository {

    public Cliente save(Cliente cliente);

    public void delete(int id);

    public void update(int id);

    public List<Cliente> findByNome(String nome);

    public List<Cliente> findByEmail(String email);

    public List<Cliente> findByTelefone(String telefone);

    public List<Cliente> findByPrestadoresFavoritosId(int prestadorId);
}
