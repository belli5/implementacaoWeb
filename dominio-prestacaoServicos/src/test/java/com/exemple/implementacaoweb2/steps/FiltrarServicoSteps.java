package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServicoRepository;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServicoService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FiltrarServicoSteps {

    private PrestacaoServicoRepository servicoRepository;
    private PrestacaoServicoService prestacaoServicoService;
    private List<PrestacaoServico> resultado;

    @Before
    public void setup() {
        servicoRepository = mock(PrestacaoServicoRepository.class);
        prestacaoServicoService = new PrestacaoServicoService(servicoRepository);

        when(servicoRepository.findAll()).thenReturn(List.of(
                new PrestacaoServico(1, "Instalação de tomadas", 150.0f, "Centro", "Eletricista", "João Eletricista"),
                new PrestacaoServico(2, "Reparo em encanamento", 200.0f, "Jardim", "Encanador", "Carlos Encanador"),
                new PrestacaoServico(3, "Troca de disjuntor", 120.0f, "Centro", "Eletricista", "João Eletricista")
        ));
    }

    @Given("que o sistema possui prestadores para as categorias {string}, {string} e {string}")
    public void categoriasPrestadores(String categoria1, String categoria2, String categoria3) {
        //Ja foi setada no setup
    }

    @When("o cliente solicitar prestadores da categoria {string}")
    public void solicitaPrestador(String categoria) {
        resultado = prestacaoServicoService.filtrarPorCategoria(categoria);
    }

    @Then("o sistema retorna apenas os prestadores associados à categoria {string}")
    public void retornoPrestador(String categoria) {
        assertNotNull(resultado);
        for (PrestacaoServico servico : resultado) {
            assertEquals(categoria, servico.getCategoria());
        }
    }

    @Given("que o sistema não possui prestadores na categoria {string}")
    public void NaoPrestadoresNaCategoria(String categoria) {
        when(servicoRepository.findAll()).thenReturn(List.of());
    }

    @Then("o sistema retorna uma lista vazia")
    public void listaVazia() {
        assertTrue(resultado.isEmpty());
    }

    @Then("informa que não há prestadores para a categoria selecionada")
    public void naoTemPrestador() {
        assertTrue(resultado.isEmpty());
    }
}
