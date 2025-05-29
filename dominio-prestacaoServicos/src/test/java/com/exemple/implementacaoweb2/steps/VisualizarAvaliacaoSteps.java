package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.avaliacao.Avaliacao;
import com.exemple.implementacaoweb2.avaliacao.TipoAvaliacao;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.pt.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class VisualizarAvaliacaoSteps {

    private final List<Avaliacao> avaliacoes = new ArrayList<>();
    private List<Avaliacao> resultadoVisualizacao = new ArrayList<>();

    @Before
    public void setup() {
        avaliacoes.clear();
        resultadoVisualizacao.clear();
    }

    @Dado("que o prestador {string} possui avaliações de clientes sobre o seus serviços: {string}, {string} e {string}")
    public void prestadorPossuiAvaliacoes(String prestador, String a1, String a2, String a3) {
        int prestadorId = 1;

        avaliacoes.add(new Avaliacao(1, prestadorId, 1, 5f, "ótimo serviço!", TipoAvaliacao.CLIENTE_AVALIA_PRESTADOR));
        avaliacoes.add(new Avaliacao(2, prestadorId, 2, 2f, "demorou para chegar", TipoAvaliacao.CLIENTE_AVALIA_PRESTADOR));
        avaliacoes.add(new Avaliacao(3, prestadorId, 3, 4f, "bem humorado", TipoAvaliacao.CLIENTE_AVALIA_PRESTADOR));
    }

    @Dado("que o prestador {string} ainda não recebeu avaliações de nenhum cliente")
    public void prestadorNaoPossuiAvaliacoes(String prestador) {
        avaliacoes.clear();
    }

    @Quando("o prestador solicita a listagem de suas avaliações recebidas")
    @Quando("ele solicita a listagem de avaliações recebidas")
    public void prestadorSlicitaAvaliacoes() {
        int prestadorId = 1;

        resultadoVisualizacao = avaliacoes.stream()
                .filter(a -> a.getPrestadorId() == prestadorId)
                .collect(Collectors.toList());
    }

    @Then("o sistema retorna todas as avaliações associadas a seus serviços concluídos, {string}, {string} e {string}")
    public void retornoComAvaliacoes(String a1, String a2, String a3) {
        assertEquals(3, resultadoVisualizacao.size());

        List<String> comentarios = resultadoVisualizacao.stream()
                .map(Avaliacao::getComentario)
                .toList();

        assertTrue(comentarios.contains("ótimo serviço!"));
        assertTrue(comentarios.contains("demorou para chegar"));
        assertTrue(comentarios.contains("bem humorado"));
    }

    @Then("o sistema informa que não há avaliações registradas para esse prestador")
    public void retornoSemAvaliacoes() {
        assertTrue(resultadoVisualizacao.isEmpty());
    }
}
