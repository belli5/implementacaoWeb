package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public interface AvaliacaoRepository {

    Avaliacao save(Avaliacao avaliacao);
    void delete(int id);
    void update(int id);

    List<Avaliacao> findByPrestadorId(int prestadorId);

}
