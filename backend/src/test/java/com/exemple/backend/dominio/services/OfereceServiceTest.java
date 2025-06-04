package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.OfereceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfereceServiceTest {

    @Mock
    private OfereceRepository ofereceRepositoryMock;

    @InjectMocks
    private OfereceService ofereceService;

    private Oferece oferece1;
    private Prestador prestador1;
    private Servico servico1;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco("Rua OfService", "Bairro OS", "Cidade OS", "OS");
        prestador1 = new Prestador(1, "Prestador Serv", "senhaPS", "ps@email.com", "111", endereco);
        servico1 = new Servico("Servico Teste", "CAT_TESTE", "Desc Servico Teste");
        oferece1 = new Oferece(1, prestador1, servico1);
    }

    @Test
    void deveListarTodosOsOferece() {
        List<Oferece> lista = Arrays.asList(oferece1, new Oferece(2, prestador1, servico1));
        when(ofereceRepositoryMock.findAll()).thenReturn(lista);

        List<Oferece> resultado = ofereceService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ofereceRepositoryMock, times(1)).findAll();
    }

    @Test
    void deveBuscarOferecePorIdExistente() {
        when(ofereceRepositoryMock.findById(1)).thenReturn(Optional.of(oferece1));

        Optional<Oferece> resultado = ofereceService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(oferece1.getId(), resultado.get().getId());
        verify(ofereceRepositoryMock, times(1)).findById(1);
    }

    @Test
    void naoDeveBuscarOferecePorIdInexistente() {
        when(ofereceRepositoryMock.findById(99)).thenReturn(Optional.empty());

        Optional<Oferece> resultado = ofereceService.buscarPorId(99);

        assertFalse(resultado.isPresent());
        verify(ofereceRepositoryMock, times(1)).findById(99);
    }

    @Test
    void deveBuscarServicosPorPrestadorId() {
        List<Servico> listaServicos = Arrays.asList(servico1);
        when(ofereceRepositoryMock.findByPrestadorId(1)).thenReturn(listaServicos);

        List<Servico> resultado = ofereceService.buscarPorPrestadorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(servico1.getNome(), resultado.get(0).getNome());
        verify(ofereceRepositoryMock, times(1)).findByPrestadorId(1);
    }

    @Test
    void deveRetornarListaVaziaDeServicosQuandoPrestadorNaoOfereceNada() {
        when(ofereceRepositoryMock.findByPrestadorId(anyInt())).thenReturn(Collections.emptyList());

        List<Servico> resultado = ofereceService.buscarPorPrestadorId(99);

        assertTrue(resultado.isEmpty());
        verify(ofereceRepositoryMock, times(1)).findByPrestadorId(99);
    }

    @Test
    void deveBuscarPrestadoresPorServicoNome() {
        List<Prestador> listaPrestadores = Arrays.asList(prestador1);
        when(ofereceRepositoryMock.findByServicoNome("Servico Teste")).thenReturn(listaPrestadores);

        List<Prestador> resultado = ofereceService.buscarPorServicoNome("Servico Teste");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(prestador1.getNome(), resultado.get(0).getNome());
        verify(ofereceRepositoryMock, times(1)).findByServicoNome("Servico Teste");
    }

    @Test
    void deveRetornarListaVaziaDePrestadoresQuandoServicoNaoOferecidoPorNinguem() {
        when(ofereceRepositoryMock.findByServicoNome(anyString())).thenReturn(Collections.emptyList());

        List<Prestador> resultado = ofereceService.buscarPorServicoNome("Servico Inexistente");

        assertTrue(resultado.isEmpty());
        verify(ofereceRepositoryMock, times(1)).findByServicoNome("Servico Inexistente");
    }

    @Test
    void deveSalvarOfereceComSucesso() {
        when(ofereceRepositoryMock.save(any(Oferece.class))).thenReturn(oferece1);

        Oferece salvo = ofereceService.salvar(oferece1);

        assertNotNull(salvo);
        assertEquals(oferece1.getId(), salvo.getId());
        verify(ofereceRepositoryMock, times(1)).save(oferece1);
    }

    @Test
    void deveDeletarOfereceComSucesso() {
        doNothing().when(ofereceRepositoryMock).delete(1);

        ofereceService.deletar(1);

        verify(ofereceRepositoryMock, times(1)).delete(1);
    }
}