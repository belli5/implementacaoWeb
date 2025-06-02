package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    @Test
    void deveMapearClienteDominioParaClienteJpa() {
        // Arrange
        Endereco enderecoDominio = new Endereco("Rua Dom", "Bairro Dom", "Cidade Dom", "DM");
        Cliente clienteDominio = new Cliente(1, "Dominio User", "domSenha", "dom@email.com", "telDom", enderecoDominio);

        // Act
        ClienteJpa clienteJpa = ClienteMapper.toClienteJpa(clienteDominio);

        // Assert
        assertNotNull(clienteJpa);
        assertEquals(clienteDominio.getId(), clienteJpa.getId());
        assertEquals(clienteDominio.getNome(), clienteJpa.getNome());
        assertEquals(clienteDominio.getEmail(), clienteJpa.getEmail());
        assertNotNull(clienteJpa.getEndereco());
        assertEquals(enderecoDominio.getRua(), clienteJpa.getEndereco().getRua());
    }

    @Test
    void deveMapearClienteJpaParaClienteDominio() {
        // Arrange
        EnderecoJpa enderecoJpa = new EnderecoJpa("Rua JpaMap", "Bairro JpaMap", "Cidade JpaMap", "JM");
        ClienteJpa clienteJpa = new ClienteJpa();
        clienteJpa.setId(2);
        clienteJpa.setNome("Jpa User");
        clienteJpa.setSenha("jpaSenha");
        clienteJpa.setEmail("jpa@email.com");
        clienteJpa.setTelefone("telJpa");
        clienteJpa.setEndereco(enderecoJpa);

        // Act
        Cliente clienteDominio = ClienteMapper.toCliente(clienteJpa);

        // Assert
        assertNotNull(clienteDominio);
        assertEquals(clienteJpa.getId(), clienteDominio.getId());
        assertEquals(clienteJpa.getNome(), clienteDominio.getNome());
        assertEquals(clienteJpa.getEmail(), clienteDominio.getEmail());
        assertNotNull(clienteDominio.getEndereco());
        assertEquals(enderecoJpa.getRua(), clienteDominio.getEndereco().getRua());
    }

    @Test
    void deveMapearClienteDominioParaClienteJpaQuandoEnderecoDominioForNulo() {
        // Arrange
        Endereco enderecoOriginal = new Endereco("Rua", "B", "C", "E");
        Cliente clienteDominio = new Cliente(1, "Dominio User", "domSenha", "dom@email.com", "telDom", enderecoOriginal);
        clienteDominio.setEndereco(null); // Simulando que o endereço pode ser nulo no objeto de domínio

        // Act
        ClienteJpa clienteJpa = ClienteMapper.toClienteJpa(clienteDominio);

        // Assert
        assertNotNull(clienteJpa);
        assertNull(clienteJpa.getEndereco(), "EnderecoJpa deveria ser nulo quando o Endereco do domínio é nulo.");
    }

    @Test
    void deveMapearClienteJpaParaClienteDominioQuandoEnderecoJpaForNulo() {
        // Arrange
        ClienteJpa clienteJpa = new ClienteJpa();
        clienteJpa.setId(3);
        clienteJpa.setNome("Jpa User Sem Endereco");

        final ClienteJpa clienteJpaComEnderecoNulo = new ClienteJpa();
        clienteJpaComEnderecoNulo.setId(3);
        clienteJpaComEnderecoNulo.setNome("Test");
        clienteJpaComEnderecoNulo.setSenha("s");
        clienteJpaComEnderecoNulo.setEmail("e");
        clienteJpaComEnderecoNulo.setTelefone("t");
        // clienteJpaComEnderecoNulo.setEndereco(null);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ClienteMapper.toCliente(clienteJpaComEnderecoNulo);
        });

        assertEquals("EnderecoJpa não pode ser nulo", exception.getMessage());
    }
}