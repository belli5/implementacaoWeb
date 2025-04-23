package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.pedidos.*;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestador.Prestador;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContratarServicoSteps {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;
    private Pedido resultado;
    private Exception excecao;
    private final Map<String, PrestacaoServico> servicosDisponiveis = new HashMap<>();
    private final Map<String, Prestador> prestadores = new HashMap<>();
    private final Map<String, Integer> clientes = new HashMap<>();

    @Before
    public void setup() {
        pedidoRepository = mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepository);
        clientes.put("Maria Flor", 1);
        clientes.put("João Carlos", 2);

        PrestacaoServico servico1 = new PrestacaoServico(1, "Conserto de geladeira", 100f, "Bairro", "Categoria", "Luiz Neto");
        PrestacaoServico servico2 = new PrestacaoServico(2, "Reparo hidráulico", 200f, "Bairro", "Categoria", "Lucas Arruda");
        PrestacaoServico servico3 = new PrestacaoServico(3, "Jardinagem", 150f, "Bairro", "Categoria", "Luiz Neto");

        Prestador prestadorLuiz = new Prestador(101, "Luiz Neto", List.of(servico1, servico3), "luiz@exemplo.com", "123456789", null);
        Prestador prestadorLucas = new Prestador(102, "Lucas Arruda", List.of(servico2), "lucas@exemplo.com", "987654321", null);

        servicosDisponiveis.put(servico1.getDescricao(), servico1);
        servicosDisponiveis.put(servico2.getDescricao(), servico2);
        servicosDisponiveis.put(servico3.getDescricao(), servico3);

        prestadores.put(prestadorLuiz.getNome(), prestadorLuiz);
        prestadores.put(prestadorLucas.getNome(), prestadorLucas);
    }

    @Given("os serviços {string}, {string} e {string} estão disponíveis")
    public void servicosDisponiveis(String s1, String s2, String s3) {
        // Este método já é implementado no setup
    }

    @Given("o prestador {string} está disponível para o dia {string}")
    public void disponibilidadeDia(String prestador, String data) {
        LocalDateTime dataHora = parseData(data);
        Prestador prestadorEncontrado = prestadores.get(prestador);
        if (prestadorEncontrado == null) {
            fail("Prestador não encontrado: " + prestador);
        }
        when(pedidoRepository.prestadorEstaDisponivel(prestadorEncontrado.getId(), dataHora)).thenReturn(true);
    }

    @Given("o prestador {string} está indisponível para o dia {string}")
    public void prestadorIndisponivelDia(String prestador, String data) {
        Prestador prestadorEncontrado = prestadores.get(prestador);
        if (prestadorEncontrado == null) {
            fail("Prestador não encontrado: " + prestador);
        }
        LocalDateTime dataHora = parseData(data);
        when(pedidoRepository.prestadorEstaDisponivel(prestadorEncontrado.getId(), dataHora)).thenReturn(false);
    }

    @When("a cliente {string} solicita a contratação do serviço {string} com o prestador {string} para o dia {string}")
    public void solicitaContratacao(String cliente, String servicoOuPrestador, String prestador, String data) {
        LocalDateTime dataHora = parseData(data);

        if (!clientes.containsKey(cliente)) {
            fail("Cliente não encontrado: " + cliente);
        }

        Prestador prestadorEncontrado = prestadores.get(prestador);
        if (prestadorEncontrado == null) {
            fail("Prestador não encontrado: " + prestador);
        }

        if (!servicosDisponiveis.containsKey(servicoOuPrestador)) {
            throw new RuntimeException("Serviço não encontrado: " + servicoOuPrestador);
        }

        PrestacaoServico servico = servicosDisponiveis.get(servicoOuPrestador);
        Pedido pedido = new Pedido(999, servico, prestadorEncontrado.getId(), clientes.get(cliente), dataHora, StatusPedido.PENDENTE);

        try {
            when(pedidoRepository.save(pedido)).thenReturn(pedido);
            resultado = pedidoService.contratarServico(pedido);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Then("o sistema registra a contratação com sucesso")
    public void contratacaoRegistrada() {
        assertNotNull(resultado);
    }

    @Then("associa o pedido de serviço {string} ao prestador {string} e à cliente {string} para o dia {string}")
    public void associaPedido(String servico, String prestador, String cliente, String data) {
        assertNotNull(resultado);
        assertEquals(servico, resultado.getServico().getDescricao());
        assertEquals(prestadores.get(prestador).getId(), resultado.getPrestadorId());
        assertEquals(clientes.get(cliente), resultado.getClienteId());
        assertEquals(parseData(data), resultado.getData());
    }

    @Then("o sistema rejeita a contratação")
    public void rejeitarContratacao() {
        assertNull(resultado);
        assertNotNull(excecao);
    }

    @Then("informa que o prestador está indisponível na data selecionada")
    public void informaIndisponibilidade() {
        assertEquals("Prestador indisponível nesta data.", excecao.getMessage());
    }

    private LocalDateTime parseData(String data) {
        return LocalDateTime.parse(data + "T00:00");
    }
}
