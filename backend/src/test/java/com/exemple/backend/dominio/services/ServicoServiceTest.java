package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.repositorys.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepositoryMock;

    @InjectMocks
    private ServicoService servicoService;

    private Servico servico1;

    @BeforeEach
    void setUp() {
        servico1 = new Servico("Limpeza Residencial", "LIMPEZA", "Serviço de limpeza completa para residências.");
    }

    @Test
    void deveSalvarServicoComSucesso() {
        when(servicoRepositoryMock.save(any(Servico.class))).thenReturn(servico1);
        Servico salvo = servicoService.save(servico1);
        assertNotNull(salvo);
        assertEquals(servico1.getNome(), salvo.getNome());
        verify(servicoRepositoryMock, times(1)).save(servico1);
    }

    @Test
    void deveListarTodosOsServicos() {
        Servico servico2 = new Servico("Jardinagem", "JARDINAGEM", "Manutenção de jardins.");
        List<Servico> lista = Arrays.asList(servico1, servico2);
        when(servicoRepositoryMock.findAll()).thenReturn(lista);

        List<Servico> resultado = servicoService.findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(servicoRepositoryMock, times(1)).findAll();
    }

    @Test
    void deveEncontrarServicosPorCategoria() {
        List<Servico> listaLimpeza = Arrays.asList(servico1);
        when(servicoRepositoryMock.findByCategoria("LIMPEZA")).thenReturn(listaLimpeza);

        List<Servico> resultado = servicoService.findByCategoria("LIMPEZA");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Limpeza Residencial", resultado.get(0).getNome());
        verify(servicoRepositoryMock, times(1)).findByCategoria("LIMPEZA");
    }

    @Test
    void deveEncontrarServicoPorNomeExistente() {
        when(servicoRepositoryMock.findByNome("Limpeza Residencial")).thenReturn(Optional.of(servico1));
        Optional<Servico> resultado = servicoService.findByNome("Limpeza Residencial");
        assertTrue(resultado.isPresent());
        assertEquals("Limpeza Residencial", resultado.get().getNome());
        verify(servicoRepositoryMock, times(1)).findByNome("Limpeza Residencial");
    }

    @Test
    void naoDeveEncontrarServicoPorNomeInexistente() {
        when(servicoRepositoryMock.findByNome(anyString())).thenReturn(Optional.empty());
        Optional<Servico> resultado = servicoService.findByNome("Inexistente");
        assertFalse(resultado.isPresent());
        verify(servicoRepositoryMock, times(1)).findByNome("Inexistente");
    }

    @Test
    void deveDeletarServicoPorNomeExistente() {
        when(servicoRepositoryMock.findByNome("Limpeza Residencial")).thenReturn(Optional.of(servico1));
        doNothing().when(servicoRepositoryMock).deleteByNome("Limpeza Residencial");

        boolean deletado = servicoService.deleteByNome("Limpeza Residencial");
        assertTrue(deletado);
        verify(servicoRepositoryMock, times(1)).findByNome("Limpeza Residencial");
        verify(servicoRepositoryMock, times(1)).deleteByNome("Limpeza Residencial");
    }

    @Test
    void naoDeveDeletarServicoPorNomeInexistente() {
        when(servicoRepositoryMock.findByNome("Inexistente")).thenReturn(Optional.empty());
        boolean deletado = servicoService.deleteByNome("Inexistente");
        assertFalse(deletado);
        verify(servicoRepositoryMock, times(1)).findByNome("Inexistente");
        verify(servicoRepositoryMock, never()).deleteByNome(anyString());
    }
}