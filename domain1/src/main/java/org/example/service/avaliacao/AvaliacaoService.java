package org.example.service.avaliacao;

import org.example.model.avaliacao.Avaliacao;
import org.example.persistence.repository.avaliacao.AvaliacaoRepository;

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