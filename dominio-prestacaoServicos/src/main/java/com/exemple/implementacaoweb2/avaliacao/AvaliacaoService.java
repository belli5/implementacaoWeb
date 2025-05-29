package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public class AvaliacaoService implements IAvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public Avaliacao cadastrarAvalicao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public void deletarAvalicao(int id) {
        avaliacaoRepository.delete(id);
    }

    @Override
    public void atualizarAvalicao(int id) {
        avaliacaoRepository.update(id);
    }

    @Override
    public List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId) {
        return avaliacaoRepository.findByPrestadorId(prestadorId);
    }
}