package com.exemple.backend.dominio.iterator;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;

import java.util.Iterator;
import java.util.List;

public class AvaliacaoSobreClienteIterator implements Iterator<AvaliacaoSobreCliente> {

    private final List<AvaliacaoSobreCliente> avaliacoes;
    private final int notaMinima;
    private int posicaoAtual = 0;

    public AvaliacaoSobreClienteIterator(List<AvaliacaoSobreCliente> avaliacoes, int notaMinima) {
        this.avaliacoes = avaliacoes;
        this.notaMinima = notaMinima;
        avancarParaProximaValida();
    }

    private void avancarParaProximaValida() {
        while (posicaoAtual < avaliacoes.size() && avaliacoes.get(posicaoAtual).getNota() < notaMinima) {
            posicaoAtual++;
        }
    }

    @Override
    public boolean hasNext() {
        return posicaoAtual < avaliacoes.size();
    }

    @Override
    public AvaliacaoSobreCliente next() {
        AvaliacaoSobreCliente atual = avaliacoes.get(posicaoAtual);
        posicaoAtual++;
        avancarParaProximaValida();
        return atual;
    }
}