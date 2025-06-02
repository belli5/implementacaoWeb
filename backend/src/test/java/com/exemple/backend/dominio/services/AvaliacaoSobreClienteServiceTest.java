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
    private AvaliacaoSobreClienteRepository repositoryMock;

    @InjectMocks
    private AvaliacaoSobreClienteService service; // A classe não está anotada com @Service

    private AvaliacaoSobreCliente avaliacao1;
    private Cliente cliente;
    private Prestador prestador;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Aval", "Bairro Aval", "Cidade Aval", "AV");
        cliente = new Cliente(1, "Cliente Aval", "s", "c@aval.com", "t", endereco);
        prestador = new Prestador(1, "Prestador Aval", "s", "p@aval.com", "t", endereco);
        avaliacao1 = new AvaliacaoSobreCliente(1, prestador, "Muito bom cliente!", 5, cliente);
    }

    @Test
    void deveContarAvaliacoesPorCliente() {
        List<AvaliacaoSobreCliente> lista = Arrays.asList(avaliacao1);
        when(repositoryMock.findByClienteId(1)).thenReturn(lista);

        List<AvaliacaoSobreCliente> resultado = service.contarAvaliacoesPorCliente(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositoryMock, times(1)).findByClienteId(1);
    }

    @Test
    void deveBuscarAvaliacaoPorIdExistente() {
        when(repositoryMock.findById(1)).thenReturn(Optional.of(avaliacao1));
        Optional<AvaliacaoSobreCliente> resultado = service.buscarPorId(1);
        assertTrue(resultado.isPresent());
        assertEquals(avaliacao1.getComentario(), resultado.get().getComentario());
        verify(repositoryMock, times(1)).findById(1);
    }

    @Test
    void deveSalvarAvaliacaoComSucesso() {
        when(repositoryMock.save(any(AvaliacaoSobreCliente.class))).thenReturn(avaliacao1);
        AvaliacaoSobreCliente salvo = service.salvarAvaliacao(avaliacao1);
        assertNotNull(salvo);
        assertEquals(avaliacao1.getNota(), salvo.getNota());
        verify(repositoryMock, times(1)).save(avaliacao1);
    }

    @Test
    void deveDeletarAvaliacaoComSucesso() {
        doNothing().when(repositoryMock).delete(1);
        service.deletarAvaliacao(1);
        verify(repositoryMock, times(1)).delete(1);
    }
}