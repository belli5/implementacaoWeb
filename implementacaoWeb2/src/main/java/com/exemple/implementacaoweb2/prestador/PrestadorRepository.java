package com.exemple.implementacaoweb2.prestador;

public interface PrestadorRepository {

    Prestador save(Prestador cliente);
    void delete(int id);
    void update (int id);

}
