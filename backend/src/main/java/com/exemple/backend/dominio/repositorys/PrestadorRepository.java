package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Prestador;

import java.util.List;
import java.util.Optional;

public interface PrestadorRepository {
    public Optional<Prestador> findById(int id);
    public List<Prestador> findAll();
    public Prestador save(Prestador prestador);
    public Prestador update(Prestador prestador);
    public void delete(int id);

}
