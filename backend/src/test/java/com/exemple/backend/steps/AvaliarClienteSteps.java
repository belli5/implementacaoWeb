package com.exemple.backend.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AvaliarClienteSteps {

    @Dado("que o prestador está logado no sistema")
    public void prestador_esta_logado() {
        // Implemente a lógica para simular o prestador logado
    }

    @Dado("o serviço com o cliente {string} foi concluído")
    public void servico_concluido(String nomeCliente) {
        // Implemente a lógica para simular um serviço concluído
    }

    @Quando("o prestador acessa o sistema")
    public void prestador_acessa_sistema() {
        // Implemente a lógica para simular o acesso ao sistema
    }

    @Quando("registra uma avaliação com nota {string} e comentário {string}")
    public void registra_avaliacao(String nota, String comentario) {
        // Implemente a lógica para simular o registro da avaliação
    }

    @Então("o sistema salva a avaliação")
    public void sistema_salva_avaliacao() {
        // Implemente a lógica para verificar se a avaliação é salva
    }

    @Então("exibe uma mensagem de confirmação do envio")
    public void exibe_mensagem_confirmacao() {
        // Implemente a lógica para verificar a exibição da mensagem de confirmação
    }

    @Dado("o serviço com o cliente {string} ainda está em andamento")
    public void servico_em_andamento(String nomeCliente) {
        // Implemente a lógica para simular um serviço em andamento
    }

    @Quando("acessar a seção {string}")
    public void acessar_secao(String secao) {
        // Implemente a lógica para simular o acesso a uma seção específica
    }

    @Então("a ação {string} não está disponível")
    public void acao_nao_disponivel(String acao) {
        // Implemente a lógica para verificar a indisponibilidade de uma ação
    }

    @Então("o sistema informa que a avaliação só pode ser feita após a conclusão do serviço")
    public void sistema_informa_avaliacao_apos_conclusao() {
        // Implemente a lógica para verificar a mensagem sobre o momento da avaliação
    }
}