package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServicoRepository;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FiltrarServicoTest {

    private PrestacaoServicoRepository servicoRepository;
    private PrestacaoServicoService prestacaoServicoService;

    @BeforeEach
    public void setUp() {
        servicoRepository = mock(PrestacaoServicoRepository.class);

        prestacaoServicoService = new PrestacaoServicoService(servicoRepository);

        when(servicoRepository.findAll()).thenReturn(Arrays.asList(
                new PrestacaoServico(1, "Instalação de tomadas", 150.0f, "Centro", "Eletricista", "João Eletricista"),
                new PrestacaoServico(2, "Reparo em encanamento", 200.0f, "Jardim", "Encanador", "Carlos Encanador"),
                new PrestacaoServico(3, "Troca de disjuntor", 120.0f, "Centro", "Eletricista", "João Eletricista")
        ));
    }

    @Test
    public void testFiltrarServicosPorCategoria() {
        List<PrestacaoServico> servicosEletricista = prestacaoServicoService.filtrarPorCategoria("Eletricista");

        assertEquals(2, servicosEletricista.size());
        for (PrestacaoServico servico : servicosEletricista) {
            assertEquals("Eletricista", servico.getCategoria());
        }
    }

}