package com.exemple.implementacaoweb2.avaliacao;

import java.util.List;

public class AvaliacaoServiceDecorator implements IAvaliacaoService {

    private final IAvaliacaoService avaliacaoService;

    public AvaliacaoServiceDecorator(IAvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @Override
    public Avaliacao cadastrarAvalicao(Avaliacao avaliacao) {
        System.out.println("Cadastrando avaliação...");
        return avaliacaoService.cadastrarAvalicao(avaliacao);
    }

    @Override
    public void deletarAvalicao(int id) {
        System.out.println("Deletando avaliação id: " + id);
        avaliacaoService.deletarAvalicao(id);
    }

    @Override
    public void atualizarAvalicao(int id) {
        System.out.println("Atualizando avaliação id: " + id);
        avaliacaoService.atualizarAvalicao(id);
    }

    @Override
    public List<Avaliacao> buscarAvaliacoesDoPrestador(int prestadorId) {
        System.out.println("Buscando avaliações do prestador id: " + prestadorId);
        return avaliacaoService.buscarAvaliacoesDoPrestador(prestadorId);
    }
}