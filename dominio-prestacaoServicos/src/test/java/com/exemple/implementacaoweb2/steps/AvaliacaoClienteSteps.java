package com.exemple.implementacaoweb2.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.pt.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class AvaliacaoClienteSteps {

    private Map<String, Boolean> statusServicos = new HashMap<>();
    private boolean avaliacaoRegistrada;
    private String mensagemErro;
    private String avaliacaoAssociada;

    @Dado("o serviço entre o prestador {string} e a cliente {string} foi concluído")
    public void servico_concluido(String prestador, String cliente) {
        statusServicos.put(prestador + "-" + cliente, true);
    }

    @Dado("o serviço entre o prestador {string} e o cliente {string} ainda está em andamento")
    public void servico_em_andamento(String prestador, String cliente) {
        statusServicos.put(prestador + "-" + cliente, false);
    }

    @Quando("o prestador {string} envia uma avaliação com nota {string} e comentário {string}")
    public void envia_avaliacao(String prestador, String nota, String comentario) {
        String chave = prestador + "-Mariana Souza";
        if (statusServicos.getOrDefault(chave, false)) {
            avaliacaoRegistrada = true;
            avaliacaoAssociada = comentario;
        } else {
            avaliacaoRegistrada = false;
            mensagemErro = "Serviço ainda não concluído";
        }
    }

    @Quando("o prestador tenta registrar uma avaliação para esse serviço")
    public void prestador_tenta_avaliar() {
        String chave = "Mateus-Paulo Lima";
        if (statusServicos.getOrDefault(chave, false)) {
            avaliacaoRegistrada = true;
        } else {
            avaliacaoRegistrada = false;
            mensagemErro = "Serviço ainda não concluído";
        }
    }

    @Then("o sistema registra a avaliação com sucesso")
    public void sistema_registra_avaliacao() {
        assertTrue(avaliacaoRegistrada);
    }

    @E("associa a avaliação ao cliente correspondente")
    public void associa_avaliacao_ao_servico() {
        assertEquals("Cliente educada e pagou em dia", avaliacaoAssociada);
    }

    @Then("o sistema rejeita a ação")
    public void sistema_rejeita_avaliacao() {
        assertFalse(avaliacaoRegistrada);
    }

    @E("informa que o cliente só pode ser avaliado após a conclusão do serviço")
    public void informa_erro_servico_nao_concluido_para_cliente() {
        assertEquals("Serviço ainda não concluído", mensagemErro);
    }
}
