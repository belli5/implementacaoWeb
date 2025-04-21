package com.group.seuservico.domain.Servicos.repository;

import com.group.seuservico.domain.Servicos.model.Servico;

public interface ServicoRepository {
    Servico save(Servico servico);
    void delete(int id);
    void update(int id);
}
