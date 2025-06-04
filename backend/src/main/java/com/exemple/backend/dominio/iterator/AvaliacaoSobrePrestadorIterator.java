package com.exemple.backend.dominio.iterator;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import java.util.List;

public class AvaliacaoSobrePrestadorIterator implements Iterator<AvaliacaoSobrePrestador> {

    private final List<AvaliacaoSobrePrestador> avaliacoes;
    private int posicao = 0;
    private final int notaMinima;

    public AvaliacaoSobrePrestadorIterator(List<AvaliacaoSobrePrestador> avaliacoes, int notaMinima) {
        this.avaliacoes = avaliacoes;
        this.notaMinima = notaMinima;
        avancar();
    }

    @Override
    public boolean hasNext() {
        return posicao < avaliacoes.size();
    }

    @Override
    public AvaliacaoSobrePrestador next() {
        AvaliacaoSobrePrestador atual = avaliacoes.get(posicao);
        posicao++;
        avancar();
        return atual;
    }

    private void avancar() {
        while (posicao < avaliacoes.size() && avaliacoes.get(posicao).getNota() < notaMinima) {
            posicao++;
        }
    }
}