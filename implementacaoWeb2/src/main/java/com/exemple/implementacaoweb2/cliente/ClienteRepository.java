package com.exemple.implementacaoweb2.cliente;

public interface ClienteRepository {

    Cliente save(Cliente cliente);
    void delete(int id);
    void update(int id);

}
