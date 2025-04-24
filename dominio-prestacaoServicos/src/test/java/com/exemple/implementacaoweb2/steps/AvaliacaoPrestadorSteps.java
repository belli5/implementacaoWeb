package com.exemple.implementacaoweb2.steps;

import io.cucumber.java.pt.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class AvaliacaoPrestadorSteps {

    private Map<String, Boolean> statusServicos = new HashMap<>();
    private boolean avaliacaoRegistrada;
    private String mensagemErro;
    private String notaInformada;

    @Dado("o serviço entre o cliente {string} e o prestador {string} foi concluído")
    public void servico_concluido(String cliente, String prestador) {
        statusServicos.put(cliente + "-" + prestador, true);
    }

    @Dado("o serviço entre o cliente {string} e o prestador {string} ainda está em andamento")
    public void servico_em_andamento(String cliente, String prestador) {
        statusServicos.put(cliente + "-" + prestador, false);
    }

    @Quando("a cliente {string} envia uma avaliação com nota {string} para o prestador {string}")
    public void cliente_envia_avaliacao(String cliente, String nota, String prestador) {
        String chave = cliente + "-" + prestador;
        if (statusServicos.getOrDefault(chave, false)) {
            notaInformada = nota;
            avaliacaoRegistrada = true;
        } else {
            avaliacaoRegistrada = false;
            mensagemErro = "Serviço ainda não concluído";
        }
    }

    @Quando("o cliente tenta enviar uma avaliação para o prestador {string}")
    public void cliente_tenta_avaliar_prestador(String prestador) {
        String chave = "Paulo Lima-" + prestador;
        if (statusServicos.getOrDefault(chave, false)) {
            avaliacaoRegistrada = true;
        } else {
            avaliacaoRegistrada = false;
            mensagemErro = "Serviço ainda não concluído";
        }
    }

    @Quando("o cliente tenta enviar uma avaliação sem informar a nota")
    public void cliente_tenta_avaliar_sem_nota() {
        String chave = "Gusttavo Lima-João";
        if (statusServicos.getOrDefault(chave, false)) {
            notaInformada = null;
            avaliacaoRegistrada = false;
            mensagemErro = "Nota obrigatória";
        }
    }

    @Então("a avaliação é registrada com sucesso no sistema")
    public void avaliacao_registrada_com_sucesso() {
        assertTrue(avaliacaoRegistrada);
    }

    @Então("o sistema rejeita a avaliação")
    public void sistema_rejeita_avaliacao() {
        assertFalse(avaliacaoRegistrada);
    }

    @Então("o sistema recusa a avaliação")
    public void sistema_recusa_avaliacao() {
        assertFalse(avaliacaoRegistrada);
    }

    @E("informa que o prestador só pode ser avaliado após a conclusão do serviço")
    public void informa_erro_servico_nao_concluido_para_prestador() {
        assertEquals("Serviço ainda não concluído", mensagemErro);
    }

    @E("indica que a nota é obrigatória para registrar a avaliação")
    public void nota_obrigatoria_para_avaliacao() {
        assertEquals("Nota obrigatória", mensagemErro);
    }
}
