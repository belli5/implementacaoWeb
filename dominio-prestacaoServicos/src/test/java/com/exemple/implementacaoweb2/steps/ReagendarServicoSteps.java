package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.pedidos.*;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ReagendarServicoSteps {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;
    private final Map<Long, Pedido> pedidosConcluidos = new HashMap<>();
    private Pedido novoPedido;
    private Exception excecao;

    @Before
    public void setup() {
        pedidoRepository = mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepository);

        PrestacaoServico servico1 = new PrestacaoServico(1, "Reparo hidráulico", 200f, "Centro", "Encanador", "Carlos");
        Pedido pedido1 = new Pedido(1, servico1, 123, 456, LocalDateTime.of(2025, 4, 10, 10, 0), StatusPedido.CONCLUIDO);
        pedidosConcluidos.put(1L, pedido1);

        PrestacaoServico servico2 = new PrestacaoServico(2, "Conserto de microondas", 180f, "Centro", "Eletrônico", "João");
        Pedido pedido2 = new Pedido(2, servico2, 123, 789, LocalDateTime.of(2025, 4, 10, 10, 0), StatusPedido.CONCLUIDO);
        pedidosConcluidos.put(2L, pedido2);
    }

    @Given("que o cliente já concluiu um serviço de {string} com o prestador {string}")
    public void conclusaoServico(String servico, String prestador) {
        Pedido pedido = pedidosConcluidos.values().stream()
                .filter(p -> p.getServico().getDescricao().equals(servico) && p.getServico().getPrestador().equals(prestador))
                .findFirst().orElse(null);
        assertNotNull(pedido);
        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));
    }

    @And("o prestador {string} esteja disponível na data {string}")
    public void prestadorDisponivel(String prestador, String data) {
        LocalDateTime dataHora = parseData(data);
        when(pedidoRepository.prestadorEstaDisponivel(anyInt(), eq(dataHora))).thenReturn(true);
    }

    @And("o prestador {string} esteja indisponível na data {string}")
    public void prestadorIndisponivel(String prestador, String data) {
        LocalDateTime dataHora = parseData(data);
        when(pedidoRepository.prestadorEstaDisponivel(anyInt(), eq(dataHora))).thenReturn(false);
    }

    @When("o cliente solicita um novo agendamento do serviço {string} com o prestador {string} para a data {string}")
    public void novoAgendamento(String servico, String prestador, String data) {
        Pedido pedido = pedidosConcluidos.values().stream()
                .filter(p -> p.getServico().getDescricao().equals(servico) && p.getServico().getPrestador().equals(prestador))
                .findFirst().orElse(null);
        assertNotNull(pedido);

        try {
            when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
            novoPedido = pedidoService.criarNovoPedido((long) pedido.getId(), parseData(data));
        } catch (Exception e) {
            excecao = e;
        }
    }

    @When("o cliente solicita um novo agendamento do mesmo serviço para essa data")
    public void reagendamento() {
        Pedido pedido = pedidosConcluidos.get(2L);
        try {
            when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
            novoPedido = pedidoService.criarNovoPedido((long) pedido.getId(), LocalDateTime.of(2025, 4, 28, 0, 0));
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Then("o sistema autoriza o reagendamento")
    public void autorizacaoReagendamento() {
        assertNotNull(novoPedido);
        assertEquals(StatusPedido.PENDENTE, novoPedido.getStatus());
    }

    @And("inicia o processo de criação de um novo pedido de serviço com os dados informados")
    public void novoPedido() {
        assertNotNull(novoPedido);
    }

    @Then("o sistema rejeita o reagendamento")
    public void rejeicaoReagendamento() {
        assertNull(novoPedido);
        assertNotNull(excecao);
    }

    @And("informa que o prestador não está disponível na data escolhida")
    public void indisponibilidade() {
        assertNotNull(excecao);
        assertEquals("Prestador indisponível nesta data.", excecao.getMessage());
    }

    private LocalDateTime parseData(String data) {
        String[] partes = data.split("/");
        return LocalDateTime.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]), 0, 0);
    }
}
