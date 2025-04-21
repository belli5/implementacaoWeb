package org.example.persistence.repository.avaliacao;

import org.example.model.avaliacao.Avaliacao;


public interface AvaliacaoRepository {

    Avaliacao save(Avaliacao avaliacao);
    void delete(int id);
    void update(int id);
    
}
