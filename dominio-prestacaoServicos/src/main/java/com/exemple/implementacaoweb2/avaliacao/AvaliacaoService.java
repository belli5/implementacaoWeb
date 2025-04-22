package com.exemple.implementacaoweb2.avaliacao;


import java.util.List;

public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public Avaliacao cadastrarAvalicao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public void deletarAvalicao(int id) {
        avaliacaoRepository.delete(id);
    }

    public void atualizarAvalicao(int id) {
        avaliacaoRepository.update(id);
    }

    public List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId) {
        return avaliacaoRepository.findByPrestadorId(prestadorId);
    }

}
