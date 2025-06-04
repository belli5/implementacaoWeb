// Salve este arquivo como:
// src/test/java/com/exemple/backend/dominio/services/ClienteServiceTest.java

package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.ClienteRepository;
import com.exemple.backend.dominio.strategies.ClienteValidadorStrategy; // Supondo que esta seja a localização
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// Se você estiver usando Transactional nos seus métodos de serviço,
// não é necessário no teste unitário deles, pois estamos mockando o repositório.
// import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @Mock // ADICIONE ESTE MOCK
    private ClienteValidadorStrategy validadorStrategyMock;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteParaTeste;
    private Endereco enderecoParaTeste;

    @BeforeEach
    void setUp() {
        enderecoParaTeste = new Endereco("Rua Exemplo Teste", "Bairro ExT", "Cidade ExT", "ET");
        // ID como Integer para corresponder ao modelo Cliente
        clienteParaTeste = new Cliente(Integer.valueOf(1), "João Silva Teste", "senha123T", "joaoteste@email.com", "999887700", enderecoParaTeste);

        // Se o método validar do strategy for void e não precisar de um comportamento específico,
        // o Mockito já faz com que ele não faça nada.
        // Se precisar garantir que não lance exceções ou que seja chamado:
        // Ex: doNothing().when(validadorStrategyMock).validar(any(Cliente.class));
    }

    @Test
    void deveCriarClienteComSucesso() {
        // Arrange
        // Se o validador for chamado antes do save, e for void:
        doNothing().when(validadorStrategyMock).validar(any(Cliente.class));
        when(clienteRepositoryMock.save(any(Cliente.class))).thenReturn(clienteParaTeste);

        // Act
        Cliente clienteSalvo = clienteService.create(clienteParaTeste);

        // Assert
        assertNotNull(clienteSalvo, "O cliente salvo não deveria ser nulo.");
        assertEquals(clienteParaTeste.getNome(), clienteSalvo.getNome());

        // Verifica se o validador foi chamado antes do save (se essa for a lógica no seu service)
        verify(validadorStrategyMock, times(1)).validar(clienteParaTeste);
        verify(clienteRepositoryMock, times(1)).save(clienteParaTeste);
    }

    @Test
    void deveEncontrarClientePorIdExistente() {
        when(clienteRepositoryMock.findById(1)).thenReturn(Optional.of(clienteParaTeste));
        Optional<Cliente> resultado = clienteService.findById(1);
        assertTrue(resultado.isPresent());
        assertEquals(clienteParaTeste.getNome(), resultado.get().getNome());
        verify(clienteRepositoryMock, times(1)).findById(1);
    }

    @Test
    void naoDeveEncontrarClientePorIdInexistente() {
        when(clienteRepositoryMock.findById(99)).thenReturn(Optional.empty());
        Optional<Cliente> resultado = clienteService.findById(99);
        assertFalse(resultado.isPresent());
        verify(clienteRepositoryMock, times(1)).findById(99);
    }

    @Test
    void deveListarTodosOsClientes() {
        List<Cliente> listaDeClientes = Collections.singletonList(clienteParaTeste);
        when(clienteRepositoryMock.findAll()).thenReturn(listaDeClientes);

        List<Cliente> resultado = clienteService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(clienteParaTeste.getNome(), resultado.get(0).getNome());
        verify(clienteRepositoryMock, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaSeNaoHouverClientes() {
        when(clienteRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        List<Cliente> resultado = clienteService.findAll();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(clienteRepositoryMock, times(1)).findAll();
    }


    @Test
    void deveAtualizarClienteExistente() {
        // Arrange
        Cliente clienteAtualizadoInfo = new Cliente(
                clienteParaTeste.getId(),
                "João Silva Atualizado",
                "novaSenhaT",
                "joao_novo_t@email.com",
                "111223355",
                enderecoParaTeste
        );
        // Garante que o cliente original "existe" para a lógica do update
        when(clienteRepositoryMock.findById(clienteParaTeste.getId())).thenReturn(Optional.of(clienteParaTeste));

        // Se o validador for chamado antes do update, e for void:
        doNothing().when(validadorStrategyMock).validar(any(Cliente.class));
        when(clienteRepositoryMock.update(any(Cliente.class))).thenReturn(clienteAtualizadoInfo);

        // Act
        Cliente resultado = clienteService.update(clienteAtualizadoInfo);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva Atualizado", resultado.getNome());
        verify(clienteRepositoryMock, times(1)).findById(clienteParaTeste.getId());
        verify(validadorStrategyMock, times(1)).validar(clienteAtualizadoInfo);
        verify(clienteRepositoryMock, times(1)).update(clienteAtualizadoInfo);
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        // Arrange
        Cliente clienteAtualizadoInfo = new Cliente(Integer.valueOf(99), "Inexistente", "senha", "inexistente@email.com", "000", enderecoParaTeste);
        // findById no service vai retornar empty, e o service vai lançar RuntimeException
        when(clienteRepositoryMock.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.update(clienteAtualizadoInfo);
        });
        assertEquals("Cliente não encontrado: 99", exception.getMessage());

        verify(clienteRepositoryMock, times(1)).findById(99);
        verify(validadorStrategyMock, never()).validar(any(Cliente.class)); // Validador não deve ser chamado
        verify(clienteRepositoryMock, never()).update(any(Cliente.class)); // Update não deve ser chamado
    }


    @Test
    void deveDeletarClienteExistente() {
        // Arrange
        // findById no service vai encontrar, e depois o delete do repo será chamado
        when(clienteRepositoryMock.findById(clienteParaTeste.getId())).thenReturn(Optional.of(clienteParaTeste));
        doNothing().when(clienteRepositoryMock).delete(clienteParaTeste.getId());

        // Act
        assertDoesNotThrow(() -> clienteService.delete(clienteParaTeste.getId()));

        // Assert
        verify(clienteRepositoryMock, times(1)).findById(clienteParaTeste.getId());
        verify(clienteRepositoryMock, times(1)).delete(clienteParaTeste.getId());
    }

    @Test
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        // Arrange
        // findById no service vai retornar empty, e o service vai lançar RuntimeException
        when(clienteRepositoryMock.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.delete(99);
        });
        assertEquals("Cliente não encontrado: 99", exception.getMessage());

        verify(clienteRepositoryMock, times(1)).findById(99);
        verify(clienteRepositoryMock, never()).delete(99); // Delete do repo não deve ser chamado
    }
}