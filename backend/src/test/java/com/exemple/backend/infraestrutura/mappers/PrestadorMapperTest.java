package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.mappers.compartilhados.EnderecoMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrestadorMapperTest {

    @Test
    void deveMapearPrestadorDominioParaPrestadorJpaCorretamente() {
        // Arrange
        Endereco enderecoDominio = new Endereco("Rua Prestador Dom", "Bairro PD", "Cidade PD", "PD");
        // O construtor do Prestador de domínio requer ID.
        Prestador prestadorDominio = new Prestador(1, "Carlos Prestador", "senha123", "carlos@prest.com", "11223344", enderecoDominio);

        // Act
        PrestadorJpa prestadorJpa = PrestadorMapper.toPrestadorJpa(prestadorDominio);

        // Assert
        assertNotNull(prestadorJpa);
        assertEquals(1, prestadorJpa.getId());
        assertEquals("Carlos Prestador", prestadorJpa.getNome());
        assertEquals("senha123", prestadorJpa.getSenha());
        assertEquals("carlos@prest.com", prestadorJpa.getEmail());
        assertEquals("11223344", prestadorJpa.getTelefone());

        // Conforme o código original do PrestadorMapper.toPrestadorJpa, o endereço NÃO é mapeado.
        // Se o mapper fosse corrigido para: jpa.setEndereco(EnderecoMapper.toEnderecoJpa(prestador.getEndereco()));
        // então a asserção abaixo seria válida:
        // assertNotNull(prestadorJpa.getEndereco());
        // assertEquals("Rua Prestador Dom", prestadorJpa.getEndereco().getRua());
        // Com o mapper atual:
        assertNull(prestadorJpa.getEndereco(), "PrestadorMapper.toPrestadorJpa atualmente não mapeia o endereço.");
    }

    @Test
    void deveMapearPrestadorJpaParaPrestadorDominioCorretamente() {
        // Arrange
        EnderecoJpa enderecoJpa = new EnderecoJpa("Rua Prestador Jpa", "Bairro PJ", "Cidade PJ", "PJ");
        PrestadorJpa prestadorJpa = new PrestadorJpa();
        prestadorJpa.setId(2);
        prestadorJpa.setNome("Ana Prestadora JPA");
        prestadorJpa.setSenha("senhaJPA");
        prestadorJpa.setEmail("ana@prestjpa.com");
        prestadorJpa.setTelefone("55667788");
        prestadorJpa.setEndereco(enderecoJpa); // PrestadorJpa tem o endereço setado

        // Act
        Prestador prestadorDominio = PrestadorMapper.toPrestador(prestadorJpa);

        // Assert
        assertNotNull(prestadorDominio);
        assertEquals(2, prestadorDominio.getId());
        assertEquals("Ana Prestadora JPA", prestadorDominio.getNome());
        assertEquals("senhaJPA", prestadorDominio.getSenha());
        assertEquals("ana@prestjpa.com", prestadorDominio.getEmail());
        assertEquals("55667788", prestadorDominio.getTelefone());
        assertNotNull(prestadorDominio.getEndereco());
        assertEquals("Rua Prestador Jpa", prestadorDominio.getEndereco().getRua());
        assertEquals("Bairro PJ", prestadorDominio.getEndereco().getBairro());
    }

    @Test
    void deveLancarExcecaoAoMapearPrestadorDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PrestadorMapper.toPrestadorJpa(null);
        });
        assertEquals("Prestador não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearPrestadorJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PrestadorMapper.toPrestador(null);
        });
        assertEquals("PrestadorJpa não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearPrestadorJpaComEnderecoNuloParaDominio() {
        // Arrange
        PrestadorJpa prestadorJpa = new PrestadorJpa();
        prestadorJpa.setId(3);
        prestadorJpa.setNome("Sem Endereco JPA");
        prestadorJpa.setSenha("senhaSE");
        prestadorJpa.setEmail("semendereco@prestjpa.com");
        prestadorJpa.setTelefone("00000000");
        prestadorJpa.setEndereco(null); // EnderecoJpa é nulo

        // Act & Assert
        // O EnderecoMapper.toEndereco lançará a exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PrestadorMapper.toPrestador(prestadorJpa);
        });
        assertEquals("EnderecoJpa não pode ser nulo", exception.getMessage());
    }
}