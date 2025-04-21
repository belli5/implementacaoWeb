package com.group.seuservico.domain.Servicos.repository;

import com.group.seuservico.domain.Servicos.model.Servico;

import java.util.List;

public interface ServicoRepository {
    Servico save(Servico servico);
    void delete(int id);
    void update(int id);
    List<Servico> buscarPorBairro(String bairro);
}
