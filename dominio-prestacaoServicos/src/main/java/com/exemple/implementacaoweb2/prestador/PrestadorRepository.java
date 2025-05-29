package com.exemple.implementacaoweb2.prestador;

import java.util.List;
import java.util.Optional;

public interface PrestadorRepository {

    public Prestador save(Prestador prestador);

    public void delete(int id);

    public void update(int id);

    public Optional<Prestador> findById(int id);

    public List<Prestador> findByServico(String servico);

    public List<Prestador> findAll();
}
