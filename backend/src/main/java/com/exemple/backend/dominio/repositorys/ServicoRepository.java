package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Servico;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository {
    //Precisa??
    public Optional<Servico> findByName(String name);
    //---------
    public List<Servico> findByCategory(String category);
    public List<Servico> findAll();
    public Servico save(Servico servico);
    public void delete(String name);
}
