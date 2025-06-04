package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobrePrestadorRepository;
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
class AvaliacaoSobrePrestadorServiceTest {

    @Mock
    private AvaliacaoSobrePrestadorRepository repositoryMock;

    @InjectMocks
    private AvaliacaoSobrePrestadorService service;

    private AvaliacaoSobrePrestador avaliacao1;
    private Cliente cliente;
    private Prestador prestador;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua AvalP", "Bairro AvalP", "Cidade AvalP", "AP");
        cliente = new Cliente(1, "Cliente AvalP", "s", "c@avalp.com", "t", endereco);
        prestador = new Prestador(1, "Prestador AvalP", "s", "p@avalp.com", "t", endereco);
        avaliacao1 = new AvaliacaoSobrePrestador(1, cliente, "Ótimo serviço!", 5, prestador);
    }

    @Test
    void deveSalvarAvaliacaoComSucesso() {
        when(repositoryMock.save(any(AvaliacaoSobrePrestador.class))).thenReturn(avaliacao1);
        AvaliacaoSobrePrestador salvo = service.save(avaliacao1);
        assertNotNull(salvo);
        assertEquals(avaliacao1.getComentario(), salvo.getComentario());
        verify(repositoryMock, times(1)).save(avaliacao1);
    }

    @Test
    void deveDeletarAvaliacaoComSucesso() {
        doNothing().when(repositoryMock).delete(1);
        service.delete(1);
        verify(repositoryMock, times(1)).delete(1);
    }

    @Test
    void deveEncontrarAvaliacoesPorPrestadorId() {
        List<AvaliacaoSobrePrestador> lista = Arrays.asList(avaliacao1);
        when(repositoryMock.findByPrestadorId(1)).thenReturn(lista);

        List<AvaliacaoSobrePrestador> resultado = service.findByPrestadorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositoryMock, times(1)).findByPrestadorId(1);
    }

    @Test
    void deveEncontrarAvaliacaoPorIdExistente() {
        when(repositoryMock.findById(1)).thenReturn(Optional.of(avaliacao1));
        Optional<AvaliacaoSobrePrestador> resultado = service.findById(1);
        assertTrue(resultado.isPresent());
        assertEquals(avaliacao1.getNota(), resultado.get().getNota());
        verify(repositoryMock, times(1)).findById(1);
    }

    @Test
    void deveListarTodasAsAvaliacoes() {
        AvaliacaoSobrePrestador avaliacao2 = new AvaliacaoSobrePrestador(2, cliente, "Podia melhorar", 3, prestador);
        List<AvaliacaoSobrePrestador> lista = Arrays.asList(avaliacao1, avaliacao2);
        when(repositoryMock.findAll()).thenReturn(lista);

        List<AvaliacaoSobrePrestador> resultado = service.findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositoryMock, times(1)).findAll();
    }
}