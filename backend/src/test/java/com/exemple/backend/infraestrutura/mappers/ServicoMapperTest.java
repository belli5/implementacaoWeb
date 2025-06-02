package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoMapperTest {

    @Test
    void deveMapearServicoDominioParaServicoJpaCorretamente() {
        // Arrange
        Servico servicoDominio = new Servico("Eletricista", "REPAROS", "Instalações e reparos elétricos.");

        // Act
        ServicoJpa servicoJpa = ServicoMapper.toServicoJpa(servicoDominio);

        // Assert
        assertNotNull(servicoJpa);
        assertEquals("Eletricista", servicoJpa.getNome());
        assertEquals("REPAROS", servicoJpa.getCategoria());
        assertEquals("Instalações e reparos elétricos.", servicoJpa.getDescricao());
    }

    @Test
    void deveMapearServicoJpaParaServicoDominioCorretamente() {
        // Arrange
        ServicoJpa servicoJpa = new ServicoJpa("Encanador", "HIDRAULICA", "Reparos e instalações hidráulicas.");

        // Act
        Servico servicoDominio = ServicoMapper.toServico(servicoJpa);

        // Assert
        assertNotNull(servicoDominio);
        assertEquals("Encanador", servicoDominio.getNome());
        assertEquals("HIDRAULICA", servicoDominio.getCategoria());
        assertEquals("Reparos e instalações hidráulicas.", servicoDominio.getDescricao());
    }

    @Test
    void deveLancarExcecaoAoMapearServicoDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ServicoMapper.toServicoJpa(null);
        });
        assertEquals("Servico não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearServicoJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ServicoMapper.toServico(null);
        });
        assertEquals("ServicoJpa não pode ser nulo", exception.getMessage());
    }
}