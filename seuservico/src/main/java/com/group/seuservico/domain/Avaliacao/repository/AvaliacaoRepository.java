package com.group.seuservico.domain.Avaliacao.repository;

import com.group.seuservico.domain.Avaliacao.model.Avaliacao;


public interface AvaliacoRepository {

    Avaliacao save(Avaliacao avaliacao);
    void delete(int id);
    void update(int id);
    
}
