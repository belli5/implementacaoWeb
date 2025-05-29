package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public class AvaliacaoServiceDecorator implements IAvaliacaoService {

    private final IAvaliacaoService avaliacaoService;

    public AvaliacaoServiceDecorator(IAvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @Override
    public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao) {
        System.out.println("Cadastrando avaliação...");
        return avaliacaoService.cadastrarAvaliacao(avaliacao);
    }

    @Override
    public void deletarAvaliacao(int id) {
        System.out.println("Deletando avaliação id: " + id);
        avaliacaoService.deletarAvaliacao(id);
    }

    @Override
    public Avaliacao atualizarAvaliacao(int id, Avaliacao novosDados) {
        System.out.println("Atualizando avaliação id: " + id);
        return avaliacaoService.atualizarAvaliacao(id, novosDados);
    }

    @Override
    public List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId) {
        System.out.println("Buscando avaliações do prestador id: " + prestadorId);
        return avaliacaoService.buscarAvaliacoesDoPrestador(prestadorId);
    }
}
