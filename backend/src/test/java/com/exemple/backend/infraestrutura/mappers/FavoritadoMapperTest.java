package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavoritadoMapperTest {

    @Test
    void deveMapearFavoritadoDominioParaFavoritadoJpa() {
        // Arrange
        // Construtor de Favoritado (domínio) requer Cliente e Prestador não nulos.
        Cliente clienteDominio = new Cliente();
        Prestador prestadorDominio = new Prestador();
        Favoritado favoritadoDominio = new Favoritado(5, clienteDominio, prestadorDominio);

        // Act
        FavoritadoJpa favoritadoJpa = FavoritadoMapper.toFavoritadoJpa(favoritadoDominio);

        // Assert
        assertNotNull(favoritadoJpa);
        assertEquals(5, favoritadoJpa.getId());
        assertNull(favoritadoJpa.getCliente(), "Mapper toFavoritadoJpa seta ClienteJpa como null."); //
        assertNull(favoritadoJpa.getPrestador(), "Mapper toFavoritadoJpa seta PrestadorJpa como null."); //
    }

    @Test
    void deveMapearFavoritadoJpaParaFavoritadoDominio() {
        // Arrange
        FavoritadoJpa favoritadoJpa = new FavoritadoJpa();
        favoritadoJpa.setId(15);
        // Mesmo se ClienteJpa e PrestadorJpa forem setados, o mapper toFavoritado os ignora.
        favoritadoJpa.setCliente(new ClienteJpa());
        favoritadoJpa.setPrestador(new PrestadorJpa());

        // Act
        Favoritado favoritadoDominio = FavoritadoMapper.toFavoritado(favoritadoJpa);

        // Assert
        assertNotNull(favoritadoDominio);
        assertEquals(15, favoritadoDominio.getId());
        assertNull(favoritadoDominio.getCliente(), "Mapper toFavoritado retorna Cliente como null."); //
        assertNull(favoritadoDominio.getPrestador(), "Mapper toFavoritado retorna Prestador como null."); //
    }

    @Test
    void deveLancarExcecaoAoMapearFavoritadoDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FavoritadoMapper.toFavoritadoJpa(null);
        });
        assertEquals("Favoritado não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearFavoritadoJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FavoritadoMapper.toFavoritado(null);
        });
        assertEquals("FavoritadoJpa não pode ser nulo", exception.getMessage());
    }
}