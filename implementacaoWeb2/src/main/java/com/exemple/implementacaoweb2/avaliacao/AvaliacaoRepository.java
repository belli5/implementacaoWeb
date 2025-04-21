package com.exemple.implementacaoweb2.avaliacao;

public interface AvaliacaoRepository {

    Avaliacao save(Avaliacao avaliacao);
    void delete(int id);
    void update(int id);

}
