package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Servico;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository {
    //Precisa??
    public Optional<Servico> findByNome(String nome);
    //---------
    public List<Servico> findByCategoria(String categoria);
    public List<Servico> findAll();
    public Servico save(Servico servico);
    public void deleteByNome(String nome);
}
