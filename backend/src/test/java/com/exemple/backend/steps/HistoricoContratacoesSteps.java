package com.exemple.backend.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HistoricoContratacoesSteps {

    @Dado("que o cliente está logado no sistema")
    public void cliente_esta_logado() {
        // Implemente a lógica para simular o cliente logado
    }

    @Dado("já contratou serviços anteriormente")
    public void cliente_contratou_servicos() {
        // Implemente a lógica para simular que o cliente já contratou serviços
    }

    @Quando("acessa o histórico de contratações")
    public void acessa_historico_contratacoes() {
        // Implemente a lógica para simular o acesso ao histórico
    }

    @Então("o sistema exibe uma lista com todos os serviços contratados")
    public void sistema_exibe_lista_servicos() {
        // Implemente a lógica para verificar a exibição da lista de serviços
    }

    @Dado("ainda não contratou nenhum serviço")
    public void cliente_nao_contratou_servicos() {
        // Implemente a lógica para simular que o cliente não contratou serviços
    }

    @Então("o sistema informa que não há serviços contratados")
    public void sistema_informa_sem_servicos() {
        // Implemente a lógica para verificar a mensagem de ausência de serviços
    }

    @Então("não exibe itens na lista")
    public void nao_exibe_itens_lista() {
        // Implemente a lógica para verificar que a lista está vazia
    }
}