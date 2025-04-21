package com.group.seuservico.domain.Avaliacao.service;

import com.group.seuservico.domain.Avaliacao.model.Avaliacao;
import com.group.seuservico.domain.Avaliacao.repository.AvaliacaoRepository;

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
    
}
