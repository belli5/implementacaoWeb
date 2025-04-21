package com.group.seuservico.domain.Avaliacao.repository;

import com.group.seuservico.domain.Avaliacao.model.Avaliacao;


public interface AvaliacaoRepository {

    Avaliacao save(Avaliacao avaliacao);
    void delete(int id);
    void update(int id);
    
}
