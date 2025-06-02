package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobrePrestadorMapperTest {

    @Test
    void deveMapearAvaliacaoSobrePrestadorDominioParaJpa() {
        // Arrange
        // Construtor de AvaliacaoSobrePrestador (domínio) requer todos os campos não nulos.
        Cliente clienteDominio = new Cliente();
        Prestador prestadorDominio = new Prestador();
        AvaliacaoSobrePrestador dominio = new AvaliacaoSobrePrestador(1, clienteDominio, "Excelente!", 5, prestadorDominio);

        // Act
        AvaliacaoSobrePrestadorJpa jpa = AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa(dominio);

        // Assert
        assertNotNull(jpa);
        assertEquals(1, jpa.getId());
        assertEquals("Excelente!", jpa.getComentario());
        assertEquals(5, jpa.getNota());
        assertNull(jpa.getCliente(), "Mapper toAvaliacaoSobrePrestadorJpa seta ClienteJpa como null."); //
        assertNull(jpa.getPrestador(), "Mapper toAvaliacaoSobrePrestadorJpa seta PrestadorJpa como null."); //
    }

    @Test
    void deveMapearAvaliacaoSobrePrestadorJpaParaDominio() {
        // Arrange
        AvaliacaoSobrePrestadorJpa jpa = new AvaliacaoSobrePrestadorJpa();
        jpa.setId(2);
        jpa.setComentario("Bom, mas pode melhorar.");
        jpa.setNota(4);
        // Mesmo se ClienteJpa e PrestadorJpa forem setados, o mapper toAvaliacaoSobrePrestador os ignora.
        jpa.setCliente(new ClienteJpa());
        jpa.setPrestador(new PrestadorJpa());

        // Act
        AvaliacaoSobrePrestador dominio = AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador(jpa);

        // Assert
        assertNotNull(dominio);
        assertEquals(2, dominio.getId());
        assertEquals("Bom, mas pode melhorar.", dominio.getComentario());
        assertEquals(4, dominio.getNota());
        assertNull(dominio.getCliente(), "Mapper toAvaliacaoSobrePrestador retorna Cliente como null."); //
        assertNull(dominio.getPrestador(), "Mapper toAvaliacaoSobrePrestador retorna Prestador como null."); //
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobrePrestadorDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa(null);
        });
        assertEquals("AvaliacaoSobrePrestador não pode ser nula", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobrePrestadorJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador(null);
        });
        assertEquals("AvaliacaoSobrePrestadorJpa não pode ser nula", exception.getMessage());
    }
}