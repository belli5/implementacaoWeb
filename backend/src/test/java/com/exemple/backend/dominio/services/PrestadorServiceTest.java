package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.PrestadorRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestadorServiceTest {

    @Mock
    private PrestadorRepository prestadorRepositoryMock;

    @InjectMocks
    private PrestadorService prestadorService;

    private Prestador prestador1;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Prest", "Bairro Prest", "Cidade Prest", "PR");
        prestador1 = new Prestador(1, "Prestador Um", "senhaP1", "p1@email.com", "111", endereco);
    }

    @Test
    void deveSalvarPrestadorComSucesso() {
        when(prestadorRepositoryMock.save(any(Prestador.class))).thenReturn(prestador1);
        Prestador salvo = prestadorService.save(prestador1);
        assertNotNull(salvo);
        assertEquals(prestador1.getNome(), salvo.getNome());
        verify(prestadorRepositoryMock, times(1)).save(prestador1);
    }

    @Test
    void deveAtualizarPrestadorComSucesso() {
        when(prestadorRepositoryMock.update(any(Prestador.class))).thenReturn(prestador1);
        Prestador atualizado = prestadorService.update(prestador1);
        assertNotNull(atualizado);
        assertEquals(prestador1.getNome(), atualizado.getNome());
        verify(prestadorRepositoryMock, times(1)).update(prestador1);
    }

    @Test
    void deveEncontrarPrestadorPorIdExistente() {
        when(prestadorRepositoryMock.findById(1)).thenReturn(Optional.of(prestador1));
        Optional<Prestador> encontrado = prestadorService.findById(1);
        assertTrue(encontrado.isPresent());
        assertEquals(prestador1.getNome(), encontrado.get().getNome());
        verify(prestadorRepositoryMock, times(1)).findById(1);
    }

    @Test
    void naoDeveEncontrarPrestadorPorIdInexistente() {
        when(prestadorRepositoryMock.findById(99)).thenReturn(Optional.empty());
        Optional<Prestador> encontrado = prestadorService.findById(99);
        assertFalse(encontrado.isPresent());
        verify(prestadorRepositoryMock, times(1)).findById(99);
    }

    @Test
    void deveListarTodosOsPrestadores() {
        Prestador prestador2 = new Prestador(2, "Prestador Dois", "senhaP2", "p2@email.com", "222", endereco);
        List<Prestador> lista = Arrays.asList(prestador1, prestador2);
        when(prestadorRepositoryMock.findAll()).thenReturn(lista);

        List<Prestador> resultado = prestadorService.findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(prestadorRepositoryMock, times(1)).findAll();
    }

    @Test
    void deveDeletarPrestadorExistente() {
        doNothing().when(prestadorRepositoryMock).delete(1);
        // O service não verifica se existe antes, apenas delega ao repositório.
        // Para um teste mais robusto, poderia-se adicionar essa verificação no service.
        prestadorService.delete(1);
        verify(prestadorRepositoryMock, times(1)).delete(1);
    }
}