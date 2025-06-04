package com.exemple.backend.infraestrutura.mappers.compartilhados;

import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoMapperTest {

    @Test
    void deveMapearEnderecoDominioParaEnderecoJpaCorretamente() {
        // Arrange
        Endereco enderecoDominio = new Endereco("Rua ABC", "Bairro XYZ", "Cidade K", "Estado W");

        // Act
        EnderecoJpa enderecoJpa = EnderecoMapper.toEnderecoJpa(enderecoDominio);

        // Assert
        assertNotNull(enderecoJpa);
        assertEquals("Rua ABC", enderecoJpa.getRua());
        assertEquals("Bairro XYZ", enderecoJpa.getBairro());
        assertEquals("Cidade K", enderecoJpa.getCidade());
        assertEquals("Estado W", enderecoJpa.getEstado());
    }

    @Test
    void deveMapearEnderecoJpaParaEnderecoDominioCorretamente() {
        // Arrange
        EnderecoJpa enderecoJpa = new EnderecoJpa("Rua 123", "Bairro 789", "Cidade M", "Estado Q");

        // Act
        Endereco enderecoDominio = EnderecoMapper.toEndereco(enderecoJpa);

        // Assert
        assertNotNull(enderecoDominio);
        assertEquals("Rua 123", enderecoDominio.getRua());
        assertEquals("Bairro 789", enderecoDominio.getBairro());
        assertEquals("Cidade M", enderecoDominio.getCidade());
        assertEquals("Estado Q", enderecoDominio.getEstado());
    }

    @Test
    void deveLancarExcecaoAoMapearEnderecoDominioNuloParaJpa() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EnderecoMapper.toEnderecoJpa(null);
        });
        assertEquals("Endereco não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearEnderecoJpaNuloParaDominio() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EnderecoMapper.toEndereco(null);
        });
        assertEquals("EnderecoJpa não pode ser nulo", exception.getMessage());
    }
}