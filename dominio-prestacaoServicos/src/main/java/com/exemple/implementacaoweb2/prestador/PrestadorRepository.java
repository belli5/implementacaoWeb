package com.exemple.implementacaoweb2.prestador;

import java.util.Optional;

public interface PrestadorRepository {

    Prestador save(Prestador prestador);
    void delete(int id);
    void update (int id);
    Optional<Prestador> findById(int id);
}