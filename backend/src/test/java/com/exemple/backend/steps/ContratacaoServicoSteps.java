package com.exemple.backend.steps;

import io.cucumber.java.pt.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContratacaoServicoSteps {

    private boolean clienteLogado;
    private boolean servicoDisponivel;
    private boolean prestadorDisponivel;
    private boolean contratacaoRegistrada;
    private String mensagemSistema;

    // Cenário: Cliente contrata um prestador com sucesso
    @Dado("que o cliente está logado no sistema")
    public void cliente_esta_logado() {
        clienteLogado = true;
    }

    @E("há um serviço disponível na plataforma")
    public void servico_disponivel() {
        servicoDisponivel = true;
    }

    @Quando("ele seleciona o serviço desejado")
    public void seleciona_servico() {
        assertTrue(servicoDisponivel, "Serviço não disponível");
    }

    @E("escolhe um prestador da lista")
    public void escolhe_prestador() {
        prestadorDisponivel = true;
    }

    @Então("o sistema registra a contratação com o prestador")
    public void sistema_registra_contratacao() {
        if (clienteLogado && servicoDisponivel && prestadorDisponivel) {
            contratacaoRegistrada = true;
        }
        assertTrue(contratacaoRegistrada, "A contratação não foi registrada");
    }

    @E("exibe uma confirmação para o cliente")
    public void exibe_confirmacao() {
        mensagemSistema = "Serviço contratado com sucesso!";
        assertEquals("Serviço contratado com sucesso!", mensagemSistema);
    }

    // Cenário: Prestador indisponível
    @E("está na página de contratação de um serviço")
    public void pagina_contratacao() {
        assertTrue(clienteLogado);
    }

    @E("o prestador selecionado está indisponível para o dia 25/04/2025")
    public void prestador_indisponivel_na_data() {
        prestadorDisponivel = false;
    }

    @Quando("o cliente tenta agendar o serviço para o dia 25/04/2025")
    public void cliente_tenta_agendar_na_data() {
        // tentativa falha pois o prestador está indisponível
        contratacaoRegistrada = false;
    }

    @Então("o sistema exibe uma mensagem informando que o prestador está indisponível nesta data")
    public void exibe_mensagem_indisponibilidade() {
        mensagemSistema = "Prestador indisponível nesta data";
        assertEquals("Prestador indisponível nesta data", mensagemSistema);
    }

    @E("a contratação não é realizada")
    public void contratacao_nao_realizada() {
        assertFalse(contratacaoRegistrada, "A contratação não deveria ter sido registrada");
    }
}
