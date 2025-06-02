package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteParaTeste;
    private Endereco enderecoParaTeste;

    @BeforeEach
    void setUp() {
        enderecoParaTeste = new Endereco("Rua Exemplo", "Bairro Ex", "Cidade Ex", "EX");
        clienteParaTeste = new Cliente(1, "João Silva", "senha123", "joao@email.com", "999887766", enderecoParaTeste);
    }

    @Test
    void deveCriarClienteComSucesso() {
        when(clienteRepositoryMock.save(any(Cliente.class))).thenReturn(clienteParaTeste);

        Cliente clienteSalvo = clienteService.create(clienteParaTeste);

        assertNotNull(clienteSalvo, "O cliente salvo não deveria ser nulo.");
        assertEquals(clienteParaTeste.getNome(), clienteSalvo.getNome(), "O nome do cliente salvo está incorreto.");
        assertEquals(clienteParaTeste.getEmail(), clienteSalvo.getEmail(), "O email do cliente salvo está incorreto.");

        verify(clienteRepositoryMock, times(1)).save(clienteParaTeste);
    }

    @Test
    void deveEncontrarClientePorIdExistente() {
        // Arrange
        when(clienteRepositoryMock.findById(1)).thenReturn(Optional.of(clienteParaTeste));

        // Act
        Optional<Cliente> resultado = clienteService.findById(1);

        // Assert
        assertTrue(resultado.isPresent(), "O cliente deveria ter sido encontrado.");
        assertEquals(clienteParaTeste.getNome(), resultado.get().getNome());
        verify(clienteRepositoryMock, times(1)).findById(1);
    }

    @Test
    void naoDeveEncontrarClientePorIdInexistente() {
        // Arrange
        when(clienteRepositoryMock.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<Cliente> resultado = clienteService.findById(99);

        // Assert
        assertFalse(resultado.isPresent(), "Nenhum cliente deveria ter sido encontrado.");
        verify(clienteRepositoryMock, times(1)).findById(99);
    }

    @Test
    void deveListarTodosOsClientes() {
        // Arrange
        List<Cliente> listaDeClientes = Collections.singletonList(clienteParaTeste);
        when(clienteRepositoryMock.findAll()).thenReturn(listaDeClientes);

        // Act
        List<Cliente> resultado = clienteService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(clienteParaTeste.getNome(), resultado.get(0).getNome());
        verify(clienteRepositoryMock, times(1)).findAll();
    }

    @Test
    void deveAtualizarClienteExistente() {
        // Arrange
        Cliente clienteAtualizadoInfo = new Cliente(1, "João Silva Atualizado", "novaSenha", "joao_novo@email.com", "111223344", enderecoParaTeste);
        when(clienteRepositoryMock.findById(1)).thenReturn(Optional.of(clienteParaTeste)); // Garante que o cliente existe
        when(clienteRepositoryMock.update(any(Cliente.class))).thenReturn(clienteAtualizadoInfo);

        // Act
        Cliente resultado = clienteService.update(clienteAtualizadoInfo);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva Atualizado", resultado.getNome());
        verify(clienteRepositoryMock, times(1)).findById(1);
        verify(clienteRepositoryMock, times(1)).update(clienteAtualizadoInfo);
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        // Arrange
        Cliente clienteAtualizadoInfo = new Cliente(99, "Inexistente", "senha", "inexistente@email.com", "000", enderecoParaTeste);
        when(clienteRepositoryMock.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.update(clienteAtualizadoInfo);
        }, "Deveria lançar RuntimeException se o cliente não for encontrado para atualização.");
        assertEquals("Cliente não encontrado: 99", exception.getMessage());
        verify(clienteRepositoryMock, times(1)).findById(99);
        verify(clienteRepositoryMock, never()).update(any(Cliente.class)); // Verifica que o update não foi chamado
    }


    @Test
    void deveDeletarClienteExistente() {
        // Arrange
        when(clienteRepositoryMock.findById(1)).thenReturn(Optional.of(clienteParaTeste));
        // doNothing() é usado para métodos void
        doNothing().when(clienteRepositoryMock).delete(1);

        // Act
        // assertDoesNotThrow verifica que nenhuma exceção é lançada
        assertDoesNotThrow(() -> clienteService.delete(1));

        // Assert
        verify(clienteRepositoryMock, times(1)).findById(1);
        verify(clienteRepositoryMock, times(1)).delete(1);
    }

    @Test
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        // Arrange
        when(clienteRepositoryMock.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.delete(99);
        });
        assertEquals("Cliente não encontrado: 99", exception.getMessage());
        verify(clienteRepositoryMock, times(1)).findById(99);
        verify(clienteRepositoryMock, never()).delete(99);
    }
}