package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.avaliacao.Avaliacao;
import com.exemple.implementacaoweb2.avaliacao.AvaliacaoService;
import com.exemple.implementacaoweb2.avaliacao.AvaliacaoRepository;
import com.exemple.implementacaoweb2.avaliacao.TipoAvaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VisualizarAvaliacoesPrestadorTest {

    private AvaliacaoService avaliacaoService;
    private AvaliacaoRepository avaliacaoRepository;

    @BeforeEach
    public void setUp() {
        avaliacaoRepository = mock(AvaliacaoRepository.class);
        avaliacaoService = new AvaliacaoService(avaliacaoRepository);

        // Cenário de avaliações existentes
        Avaliacao avaliacao1 = new Avaliacao(1, 10, 100, 5.0f, "Ótimo serviço!", TipoAvaliacao.CLIENTE_AVALIA_PRESTADOR);
        Avaliacao avaliacao2 = new Avaliacao(2, 10, 101, 4.0f, "Muito bom!", TipoAvaliacao.PRESTADOR_AVALIA_CLIENTE);

        when(avaliacaoRepository.findByPrestadorId(10)).thenReturn(Arrays.asList(avaliacao1, avaliacao2));

        // Cenário de nenhuma avaliação
        when(avaliacaoRepository.findByPrestadorId(20)).thenReturn(Collections.emptyList());
    }

    @Test
    public void testVisualizarAvaliacoesRecebidasComSucesso() {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesDoPrestador(10);

        assertNotNull(avaliacoes);
        assertEquals(2, avaliacoes.size());

        assertEquals(5.0f, avaliacoes.get(0).getNota());
        assertEquals("Ótimo serviço!", avaliacoes.get(0).getComentario());
        assertEquals(100, avaliacoes.get(0).getClienteId()); // Aqui é o ID, depois no front pode buscar nome do cliente

        assertEquals(4.0f, avaliacoes.get(1).getNota());
        assertEquals("Muito bom!", avaliacoes.get(1).getComentario());
        assertEquals(101, avaliacoes.get(1).getClienteId());

        verify(avaliacaoRepository, times(1)).findByPrestadorId(10);
    }

    @Test
    public void testVisualizarSemAvaliacoes() {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesDoPrestador(20);

        assertNotNull(avaliacoes);
        assertTrue(avaliacoes.isEmpty()); // <- Verifica que a lista está vazia!

        verify(avaliacaoRepository, times(1)).findByPrestadorId(20);
    }
}
