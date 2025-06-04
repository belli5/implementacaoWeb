package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobreClienteRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoSobreClienteServiceTest {

    @Mock
    private AvaliacaoSobreClienteRepository avaliacaoSobreClienteRepositoryMock;

    @InjectMocks
    private AvaliacaoSobreClienteService avaliacaoSobreClienteService;

    private AvaliacaoSobreCliente avaliacao1;
    private Cliente cliente1;
    private Prestador prestador1;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco("Rua Teste", "Bairro Teste", "Cidade Teste", "TS");
        cliente1 = new Cliente(1, "Cliente Teste", "senhaC", "cliente@email.com", "111111111", endereco);
        prestador1 = new Prestador(1, "Prestador Teste", "senhaP", "prestador@email.com", "222222222", endereco);
        avaliacao1 = new AvaliacaoSobreCliente(1, prestador1, "Comentário teste", 5, cliente1);
    }

    @Test
    void deveEncontrarAvaliacaoPorIdExistente() {
        when(avaliacaoSobreClienteRepositoryMock.findById(1)).thenReturn(Optional.of(avaliacao1));

        Optional<AvaliacaoSobreCliente> resultado = avaliacaoSobreClienteService.findById(1);

        assertTrue(resultado.isPresent());
        assertEquals(avaliacao1.getId(), resultado.get().getId());
        verify(avaliacaoSobreClienteRepositoryMock, times(1)).findById(1);
    }

    @Test
    void naoDeveEncontrarAvaliacaoPorIdInexistente() {
        when(avaliacaoSobreClienteRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        Optional<AvaliacaoSobreCliente> resultado = avaliacaoSobreClienteService.findById(99);

        assertFalse(resultado.isPresent());
        verify(avaliacaoSobreClienteRepositoryMock, times(1)).findById(99);
    }

    @Test
    void deveEncontrarTodasAsAvaliacoes() {
        List<AvaliacaoSobreCliente> lista = Arrays.asList(avaliacao1, new AvaliacaoSobreCliente(2, prestador1, "Outro comentário", 4, cliente1));
        when(avaliacaoSobreClienteRepositoryMock.findAll()).thenReturn(lista);

        List<AvaliacaoSobreCliente> resultado = avaliacaoSobreClienteService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(avaliacaoSobreClienteRepositoryMock, times(1)).findAll();
    }

    @Test
    void deveEncontrarAvaliacoesPorClienteId() {
        List<AvaliacaoSobreCliente> lista = Arrays.asList(avaliacao1);
        when(avaliacaoSobreClienteRepositoryMock.findByClienteId(1)).thenReturn(lista);

        List<AvaliacaoSobreCliente> resultado = avaliacaoSobreClienteService.findByClienteId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(avaliacao1.getId(), resultado.get(0).getId());
        verify(avaliacaoSobreClienteRepositoryMock, times(1)).findByClienteId(1);
    }

    @Test
    void deveSalvarAvaliacaoComSucesso() {
        when(avaliacaoSobreClienteRepositoryMock.save(any(AvaliacaoSobreCliente.class))).thenReturn(avaliacao1);

        AvaliacaoSobreCliente salvo = avaliacaoSobreClienteService.save(avaliacao1);

        assertNotNull(salvo);
        assertEquals(avaliacao1.getId(), salvo.getId());
        verify(avaliacaoSobreClienteRepositoryMock, times(1)).save(avaliacao1);
    }

    @Test
    void deveDeletarAvaliacaoComSucesso() {
        doNothing().when(avaliacaoSobreClienteRepositoryMock).delete(1);

        avaliacaoSobreClienteService.delete(1);

        verify(avaliacaoSobreClienteRepositoryMock, times(1)).delete(1);
    }
}