package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public interface AvaliacaoRepository {

    public Avaliacao save(Avaliacao avaliacao);
    public void delete(int id);
    public void update(int id);

    public List<Avaliacao> findByPrestadorId(int prestadorId);

}
