package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestador.Prestador;
import com.exemple.implementacaoweb2.prestador.PrestadorRepository;
import com.exemple.implementacaoweb2.prestador.PrestadorService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditarPerfilSteps {

    private PrestadorRepository prestadorRepository;
    private PrestadorService prestadorService;
    private Prestador prestador;
    private PrestacaoServico servicoNovo;
    private Exception excecao;

    @Before
    public void setup() {
        prestadorRepository = mock(PrestadorRepository.class);
        prestadorService = new PrestadorService(prestadorRepository);
    }

    @Given("que o prestador oferece apenas {string}")
    public void prestadorComServico(String servico) {
        String nome = "João";
        PrestacaoServico servicoInicial = new PrestacaoServico(1, servico, 100f, "Bairro", "Categoria", nome);
        prestador = new Prestador(101, nome, new ArrayList<>(List.of(servicoInicial)), "email@email.com", "123456789", null);
        when(prestadorRepository.findById(101)).thenReturn(java.util.Optional.of(prestador));
    }

    @When("ele adiciona {string} à sua lista de serviços e confirma a edição")
    public void adicionaServico(String nomeServico) {
        servicoNovo = new PrestacaoServico(2, nomeServico, 150f, "Bairro", "Categoria", prestador.getNome());
        prestador.adicionarServico(servicoNovo);

        try {
            prestadorService.atualizarPrestador(prestador);
        } catch (Exception e) {
            this.excecao = e;
        }
    }


    @Then("o sistema atualiza os serviços ofertados para incluir {string} e {string}")
    public void sistemaAtualizaServicos(String s1, String s2) {
        List<String> servicos = prestador.getServicos().stream().map(PrestacaoServico::getDescricao).toList();
        assertTrue(servicos.contains(s1));
        assertTrue(servicos.contains(s2));
    }

    @Given("que o prestador deseja adicionar {string} aos seus serviços")
    public void quePrestadorDesejaAdicionarAosServicos(String nomeServico) {
        String nome = "João";
        prestador = new Prestador(101, nome, new ArrayList<>(), "email@email.com", "123456789", null);
        servicoNovo = new PrestacaoServico(3, nomeServico, 120f, "Bairro", "Categoria", nome);
        servicoNovo.setDescricao(null);
    }

    @When("ele tenta salvar a edição sem preencher a descrição do serviço")
    public void salvarSemDescricao() throws Exception {
        try {
            prestador.adicionarServico(servicoNovo);
            prestadorService.atualizarPrestador(prestador);
        } catch (Exception e) {
            this.excecao = e;
        }
    }

    @Then("o sistema rejeita a operação")
    public void sistemaRejeitaOperacao() {
        assertNotNull(excecao, "A exceção não foi gerada. Esperada: Exceção ao tentar adicionar serviço sem descrição.");
    }

    @Then("informa que a descrição do serviço é obrigatória")
    public void mensagemDescricaoObrigatoria() {
        assertEquals("Descrição do serviço é obrigatória.", excecao.getMessage());
    }
}
