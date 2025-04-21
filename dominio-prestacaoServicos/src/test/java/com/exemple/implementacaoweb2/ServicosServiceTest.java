package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestador.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServicosServiceTest {

    private ServicosService servicosService;

    @BeforeEach
    public void setUp() {
        servicosService = new ServicosService();
    }

    @Test
    public void testAdicionarServicoComSucesso() {
        PrestacaoServico servico = new PrestacaoServico(1, "Instalação elétrica", "Instalação de fiação", 150.0f, "Boa Viagem");
        String resultado = servicosService.adicionarServico(servico);
        assertEquals("Serviço adicionado com sucesso!", resultado);
    }

    @Test
    public void testAdicionarServicoDuplicado() {
        PrestacaoServico servico = new PrestacaoServico(1, "Instalação elétrica", "Instalação de fiação", 150.0f, "Boa Viagem");
        servicosService.adicionarServico(servico);
        String resultado = servicosService.adicionarServico(servico);
        assertEquals("Não é possível adicionar o servico duas vezes.", resultado);
    }

    @Test
    public void testRemoverServicoComSucesso() {
        PrestacaoServico servico = new PrestacaoServico(1, "Instalação elétrica", "Instalação de fiação", 150.0f, "Boa Viagem");
        servicosService.adicionarServico(servico);
        String resultado = servicosService.removerServico(servico);
        assertEquals("Serviço removido com sucesso!", resultado);
    }

    @Test
    public void testAdicionarServicoComNomeVazio() {
        ServicosService servicosService = new ServicosService();
        PrestacaoServico servicoInvalido = new PrestacaoServico(1, "", "Descrição teste", 100.0f, "Recife");

        String resultado = servicosService.adicionarServico(servicoInvalido);
        assertEquals("Nome do serviço é obrigatório.", resultado);
    }

}
