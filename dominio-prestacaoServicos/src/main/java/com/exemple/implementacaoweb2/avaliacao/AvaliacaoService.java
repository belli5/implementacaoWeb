package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public class AvaliacaoService implements IAvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public void deletarAvaliacao(int id) {
        avaliacaoRepository.delete(id);
    }

    @Override
    public Avaliacao atualizarAvaliacao(int id, Avaliacao novosDados) {
        // Aqui você pode buscar o existente e atualizar — ou simplesmente sobrescrever com os novos dados.
        Avaliacao atualizada = new Avaliacao(
                id,
                novosDados.getPrestadorId(),
                novosDados.getClienteId(),
                novosDados.getNota(),
                novosDados.getComentario(),
                novosDados.getTipoAvaliacao()
        );
        return avaliacaoRepository.save(atualizada);
    }


    @Override
    public List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId) {
        return avaliacaoRepository.findByPrestadorId(prestadorId);
    }
}
