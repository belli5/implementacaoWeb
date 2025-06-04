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
        Endereco enderecoDominio = new Endereco("Rua Prestador Dom", "Bairro PD", "Cidade PD", "PD");
        Prestador prestadorDominio = new Prestador(1, "Carlos Prestador", "senha123", "carlos@prest.com", "11223344", enderecoDominio);

        PrestadorJpa prestadorJpa = PrestadorMapper.toPrestadorJpa(prestadorDominio);

        assertNotNull(prestadorJpa);
        assertEquals(1, prestadorJpa.getId());
        assertEquals("Carlos Prestador", prestadorJpa.getNome());
        assertEquals("senha123", prestadorJpa.getSenha());
        assertEquals("carlos@prest.com", prestadorJpa.getEmail());
        assertEquals("11223344", prestadorJpa.getTelefone());

        assertNotNull(prestadorJpa.getEndereco());
        assertEquals("Rua Prestador Dom", prestadorJpa.getEndereco().getRua());
    }

    @Test
    void deveMapearPrestadorJpaParaPrestadorDominioCorretamente() {
        EnderecoJpa enderecoJpa = new EnderecoJpa("Rua Prestador Jpa", "Bairro PJ", "Cidade PJ", "PJ");
        PrestadorJpa prestadorJpa = new PrestadorJpa();
        prestadorJpa.setId(2);
        prestadorJpa.setNome("Ana Prestadora JPA");
        prestadorJpa.setSenha("senhaJPA");
        prestadorJpa.setEmail("ana@prestjpa.com");
        prestadorJpa.setTelefone("55667788");
        prestadorJpa.setEndereco(enderecoJpa);

        Prestador prestadorDominio = PrestadorMapper.toPrestador(prestadorJpa);

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
        PrestadorJpa prestadorJpaComEnderecoNulo = new PrestadorJpa();
        prestadorJpaComEnderecoNulo.setId(3);
        prestadorJpaComEnderecoNulo.setNome("Sem Endereco JPA");
        prestadorJpaComEnderecoNulo.setSenha("senhaSE");
        prestadorJpaComEnderecoNulo.setEmail("semendereco@prestjpa.com");
        prestadorJpaComEnderecoNulo.setTelefone("00000000");
        prestadorJpaComEnderecoNulo.setEndereco(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PrestadorMapper.toPrestador(prestadorJpaComEnderecoNulo);
        });
        assertEquals("Endereço do prestador não pode ser nulo", exception.getMessage());
    }
}