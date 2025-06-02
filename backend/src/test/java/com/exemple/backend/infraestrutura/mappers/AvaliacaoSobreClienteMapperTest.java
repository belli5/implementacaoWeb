package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobreClienteMapperTest {

    @Test
    void deveMapearAvaliacaoSobreClienteDominioParaJpa() {
        // Arrange
        // Construtor de AvaliacaoSobreCliente (domínio) requer Prestador, Comentario, Cliente não nulos.
        Prestador prestadorDominio = new Prestador();
        Cliente clienteDominio = new Cliente();
        AvaliacaoSobreCliente dominio = new AvaliacaoSobreCliente(1, prestadorDominio, "Cliente exemplar!", 5, clienteDominio);

        // Act
        AvaliacaoSobreClienteJpa jpa = AvaliacaoSobreClienteMapper.toAvaliacaoSobreClienteJpa(dominio);

        // Assert
        assertNotNull(jpa);
        assertEquals(1, jpa.getId());
        assertEquals("Cliente exemplar!", jpa.getComentario());
        assertEquals(5, jpa.getNota());
        assertNull(jpa.getCliente(), "Mapper toAvaliacaoSobreClienteJpa seta ClienteJpa como null."); //
        assertNull(jpa.getPrestador(), "Mapper toAvaliacaoSobreClienteJpa seta PrestadorJpa como null."); //
    }

    @Test
    void deveMapearAvaliacaoSobreClienteJpaParaDominio() {
        // Arrange
        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(2);
        jpa.setComentario("Comunicação fácil.");
        jpa.setNota(4);

        // Para o mapper toAvaliacaoSobreCliente, PrestadorJpa e ClienteJpa no objeto 'jpa'
        // NÃO PODEM ser nulos, pois ele tenta acessar jpa.getPrestador().getId() e jpa.getCliente().getId().
        PrestadorJpa prestadorJpaMock = new PrestadorJpa();
        prestadorJpaMock.setId(101); // ID que será usado pelo mapper
        jpa.setPrestador(prestadorJpaMock);

        ClienteJpa clienteJpaMock = new ClienteJpa();
        clienteJpaMock.setId(202); // ID que será usado pelo mapper
        jpa.setCliente(clienteJpaMock);

        // Act
        AvaliacaoSobreCliente dominio = AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(jpa);

        // Assert
        assertNotNull(dominio);
        assertEquals(2, dominio.getId());
        assertEquals("Comunicação fácil.", dominio.getComentario());
        assertEquals(4, dominio.getNota());

        assertNotNull(dominio.getCliente(), "Cliente no domínio não deveria ser nulo.");
        assertEquals(202, dominio.getCliente().getId(), "ID do Cliente não corresponde.");
        // Outros campos do Cliente (nome, etc.) serão nulos porque o mapper cria `new Cliente(id, null, null...)`

        assertNotNull(dominio.getPrestador(), "Prestador no domínio não deveria ser nulo.");
        assertEquals(101, dominio.getPrestador().getId(), "ID do Prestador não corresponde.");
        // Outros campos do Prestador (nome, etc.) serão nulos
    }

    @Test
    void deveLancarNullPointerExceptionAoMapearJpaParaDominioComPrestadorJpaNulo() {
        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(3);
        jpa.setComentario("Teste");
        jpa.setNota(3);
        jpa.setPrestador(null); // Prestador Jpa é nulo
        jpa.setCliente(new ClienteJpa()); // Cliente Jpa não nulo para isolar o problema

        assertThrows(NullPointerException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(jpa);
        }, "Deveria lançar NPE se PrestadorJpa for nulo, pois o mapper tenta jpa.getPrestador().getId()");
    }

    @Test
    void deveLancarNullPointerExceptionAoMapearJpaParaDominioComClienteJpaNulo() {
        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(4);
        jpa.setComentario("Teste");
        jpa.setNota(3);
        jpa.setPrestador(new PrestadorJpa()); // Prestador Jpa não nulo
        jpa.setCliente(null); // Cliente Jpa é nulo

        assertThrows(NullPointerException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(jpa);
        }, "Deveria lançar NPE se ClienteJpa for nulo, pois o mapper tenta jpa.getCliente().getId()");
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobreClienteDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreClienteJpa(null);
        });
        assertEquals("AvaliacaoSobreCliente não pode ser nula", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobreClienteJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(null);
        });
        assertEquals("AvaliacaoSobreClienteJpa não pode ser nula", exception.getMessage());
    }
}