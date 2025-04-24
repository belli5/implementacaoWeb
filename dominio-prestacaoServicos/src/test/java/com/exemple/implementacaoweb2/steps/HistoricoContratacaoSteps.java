package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.pedidos.*;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HistoricoContratacaoSteps {

    private final Map<String, PrestacaoServico> servicosDisponiveis = new HashMap<>();
    private List<Pedido> historico;

    @Before
    public void setup() {
        historico = new ArrayList<>();

        PrestacaoServico servico1 = new PrestacaoServico(1, "Conserto de microondas", 100f, "Bairro", "Categoria", "Luiz Neto");
        PrestacaoServico servico2 = new PrestacaoServico(2, "Reparo hidráulico", 200f, "Bairro", "Categoria", "Lucas Arruda");
        PrestacaoServico servico3 = new PrestacaoServico(3, "Instalação elétrica", 150f, "Bairro", "Categoria", "Luiz Neto");

        servicosDisponiveis.put(servico1.getDescricao(), servico1);
        servicosDisponiveis.put(servico2.getDescricao(), servico2);
        servicosDisponiveis.put(servico3.getDescricao(), servico3);
    }

    @Given("o cliente já contratou serviços anteriormente sendo eles {string}, {string} e {string}")
    public void existeContratacao(String desc1, String desc2, String desc3) {
        PrestacaoServico s1 = servicosDisponiveis.get(desc1);
        PrestacaoServico s2 = servicosDisponiveis.get(desc2);
        PrestacaoServico s3 = servicosDisponiveis.get(desc3);

        Pedido p1 = new Pedido(1, s1, 101, 1, LocalDateTime.now().minusDays(10), StatusPedido.CONCLUIDO);
        Pedido p2 = new Pedido(2, s2, 102, 1, LocalDateTime.now().minusDays(7), StatusPedido.CONCLUIDO);
        Pedido p3 = new Pedido(3, s3, 103, 1, LocalDateTime.now().minusDays(3), StatusPedido.CONCLUIDO);

        historico.add(p1);
        historico.add(p2);
        historico.add(p3);
    }

    @Given("que o cliente ainda não contratou nenhum serviço")
    public void nenhumServicoContratado() {
        historico.clear();
    }

    @When("o cliente acessa o histórico de contratações")
    public void clienteAcessaHistorico() {
        //aqui só acessos o histórico, a exibição é feita no then
    }

    @Then("o sistema exibe uma lista com todos os serviços que já foram contratados, {string}, {string} e {string}")
    public void sistemaExibeHistorico(String s1, String s2, String s3) {
        assertNotNull(historico);
        assertEquals(3, historico.size());

        List<String> descricoes = historico.stream()
                .map(p -> p.getServico().getDescricao())
                .toList();

        assertTrue(descricoes.contains(s1));
        assertTrue(descricoes.contains(s2));
        assertTrue(descricoes.contains(s3));

        System.out.println("Histórico de Contratações:");
        for (Pedido pedido : historico) {
            System.out.println("Serviço contratado: " + pedido.getServico().getDescricao() + ", Data: " + pedido.getData());
        }
    }

    @Then("o sistema não informa nenhum item na lista")
    public void sistemaNaoExibeHistorico() {
        assertNotNull(historico);
        assertEquals(0, historico.size());

        System.out.println("Histórico de Contratações:");
        System.out.println("Nenhuma contratação realizada.");
    }
}