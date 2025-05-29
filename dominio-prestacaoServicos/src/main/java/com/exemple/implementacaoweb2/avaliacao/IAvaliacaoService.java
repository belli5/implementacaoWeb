package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public interface IAvaliacaoService {
    Avaliacao cadastrarAvaliacao(Avaliacao avaliacao);
    void deletarAvaliacao(int id);
    Avaliacao atualizarAvaliacao(int id, Avaliacao novosDados);

    List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId);
}
