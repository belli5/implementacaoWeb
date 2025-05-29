package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public interface IAvaliacaoService {
    Avaliacao cadastrarAvalicao(Avaliacao avaliacao);
    void deletarAvalicao(int id);
    void atualizarAvalicao(int id);
    List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId);
}
