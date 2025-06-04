package com.exemple.backend.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReagendarServicoSteps {

    @Dado("que o cliente está logado no sistema")
    public void cliente_esta_logado() {
        // Implemente a lógica para simular o cliente logado
    }

    @Dado("já concluiu um serviço com o prestador {string}")
    public void cliente_concluiu_servico(String nomePrestador) {
        // Implemente a lógica para simular um serviço concluído com um prestador específico
    }

    @Quando("acessar o histórico de contratações")
    public void acessar_historico_contratacoes() {
        // Implemente a lógica para simular o acesso ao histórico
    }

    @Quando("solicita o reagendamento do serviço")
    public void solicita_reagendamento() {
        // Implemente a lógica para simular a solicitação de reagendamento
    }

    @Então("o sistema exibe o formulário de agendamento")
    public void sistema_exibe_formulario() {
        // Implemente a lógica para verificar a exibição do formulário
    }

    @Quando("solicita o reagendamento de um serviço para a data {string}")
    public void solicita_reagendamento_data(String data) {
        // Implemente a lógica para simular a solicitação de reagendamento para uma data específica
    }

    @Dado("acessa o histórico de contratações")
    public void acessa_historico_contratacoes() {
        // Implemente a lógica para simular o acesso ao histórico
    }

    @Dado("o prestador estiver indisponível nesse dia")
    public void prestador_indisponivel() {
        // Implemente a lógica para simular a indisponibilidade do prestador
    }

    @Então("o sistema informa que o prestador não está disponível na data escolhida")
    public void sistema_informa_prestador_indisponivel() {
        // Implemente a lógica para verificar a mensagem de indisponibilidade
    }

    @Então("impede a finalização do agendamento")
    public void impede_finalizacao_agendamento() {
        // Implemente a lógica para verificar se o agendamento é impedido
    }
}