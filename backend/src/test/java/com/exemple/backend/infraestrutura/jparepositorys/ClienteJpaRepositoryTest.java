package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    private ClienteJpa clienteJpa1;
    private ClienteJpa clienteJpa2;

    @BeforeEach
    void setUp() {
        clienteJpaRepository.deleteAll();

        EnderecoJpa endereco1 = new EnderecoJpa("Rua JPA 1", "Bairro JPA1", "Cidade JPA1", "J1");
        clienteJpa1 = new ClienteJpa(); // Usando construtor padrão e setters para ClienteJpa
        clienteJpa1.setNome("Cliente JPA Um");
        clienteJpa1.setSenha("senhaJPA1");
        clienteJpa1.setEmail("jpa1@email.com");
        clienteJpa1.setTelefone("telJPA1");
        clienteJpa1.setEndereco(endereco1);

        EnderecoJpa endereco2 = new EnderecoJpa("Rua JPA 2", "Bairro JPA2", "Cidade JPA2", "J2");
        clienteJpa2 = new ClienteJpa();
        clienteJpa2.setNome("Cliente JPA Dois");
        clienteJpa2.setSenha("senhaJPA2");
        clienteJpa2.setEmail("jpa2@email.com");
        clienteJpa2.setTelefone("telJPA2");
        clienteJpa2.setEndereco(endereco2);
    }

    @Test
    void deveSalvarEEncontrarClientePorId() {
        // Arrange
        ClienteJpa clienteSalvo = entityManager.persistAndFlush(clienteJpa1);
        assertNotNull(clienteSalvo.getId(), "ID não deveria ser nulo após persistir");

        // Act
        Optional<ClienteJpa> encontrado = clienteJpaRepository.findById(clienteSalvo.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(clienteSalvo.getNome(), encontrado.get().getNome());
        assertEquals(clienteSalvo.getEndereco().getRua(), encontrado.get().getEndereco().getRua());
    }

    @Test
    void deveRetornarOptionalVazioSeIdNaoExistir() {
        // Act
        Optional<ClienteJpa> encontrado = clienteJpaRepository.findById(999); // ID inexistente

        // Assert
        assertFalse(encontrado.isPresent());
    }

    @Test
    void deveEncontrarTodosOsClientes() {
        // Arrange
        entityManager.persist(clienteJpa1);
        entityManager.persist(clienteJpa2);
        entityManager.flush();

        // Act
        List<ClienteJpa> todos = clienteJpaRepository.findAll();

        // Assert
        assertNotNull(todos);
        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(c -> c.getNome().equals("Cliente JPA Um")));
        assertTrue(todos.stream().anyMatch(c -> c.getNome().equals("Cliente JPA Dois")));
    }

    @Test
    void deveDeletarClientePorId() {
        // Arrange
        ClienteJpa clienteSalvo = entityManager.persistAndFlush(clienteJpa1);
        int idParaDeletar = clienteSalvo.getId();

        // Act
        clienteJpaRepository.deleteById(idParaDeletar);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<ClienteJpa> encontrado = clienteJpaRepository.findById(idParaDeletar);
        assertFalse(encontrado.isPresent(), "Cliente não deveria ser encontrado após deletar.");
    }

    @Test
    void deveAtualizarCliente() {
        // Arrange
        ClienteJpa clientePersistido = entityManager.persistAndFlush(clienteJpa1);
        assertNotNull(clientePersistido.getId());

        Optional<ClienteJpa> clienteParaAtualizarOpt = clienteJpaRepository.findById(clientePersistido.getId());
        assertTrue(clienteParaAtualizarOpt.isPresent());

        ClienteJpa clienteParaAtualizar = clienteParaAtualizarOpt.get();
        clienteParaAtualizar.setNome("Nome JPA Atualizado");
        clienteParaAtualizar.getEndereco().setRua("Rua JPA Atualizada");

        // Act
        ClienteJpa clienteAtualizado = clienteJpaRepository.save(clienteParaAtualizar); // save também atualiza
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<ClienteJpa> clienteVerificadoOpt = clienteJpaRepository.findById(clientePersistido.getId());
        assertTrue(clienteVerificadoOpt.isPresent());
        assertEquals("Nome JPA Atualizado", clienteVerificadoOpt.get().getNome());
        assertEquals("Rua JPA Atualizada", clienteVerificadoOpt.get().getEndereco().getRua());
        assertEquals(clienteAtualizado.getId(), clienteVerificadoOpt.get().getId());
    }
}