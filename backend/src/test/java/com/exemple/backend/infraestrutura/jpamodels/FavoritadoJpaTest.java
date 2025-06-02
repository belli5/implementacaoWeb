package com.exemple.backend.infraestrutura.jpamodels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FavoritadoJpaTest {

    @Test
    void testConstrutorPadrao() {
        FavoritadoJpa favoritado = new FavoritadoJpa();
        assertNotNull(favoritado);
        assertEquals(0, favoritado.getId(), "ID (int primitivo) deveria ser 0 por padrão");
        assertNull(favoritado.getPrestador(), "Prestador deveria ser nulo por padrão");
        assertNull(favoritado.getCliente(), "Cliente deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        PrestadorJpa prestador = new PrestadorJpa();
        ClienteJpa cliente = new ClienteJpa();
        FavoritadoJpa favoritado = new FavoritadoJpa(1, prestador, cliente);

        assertEquals(1, favoritado.getId());
        assertSame(prestador, favoritado.getPrestador());
        assertSame(cliente, favoritado.getCliente());
    }

    @Test
    void testGettersAndSetters() {
        FavoritadoJpa favoritado = new FavoritadoJpa();
        favoritado.setId(15);
        PrestadorJpa prestador = new PrestadorJpa();
        favoritado.setPrestador(prestador);
        ClienteJpa cliente = new ClienteJpa();
        favoritado.setCliente(cliente);

        assertEquals(15, favoritado.getId());
        assertSame(prestador, favoritado.getPrestador());
        assertSame(cliente, favoritado.getCliente());
    }

    @Test
    void testToString() {
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(33);
        ClienteJpa cliente = new ClienteJpa();
        cliente.setId(99);

        FavoritadoJpa favoritado = new FavoritadoJpa(2, prestador, cliente);
        String expected = "FavoritadoJpa{id=2, prestadorId=33, clienteId=99}"; //
        assertEquals(expected, favoritado.toString());
    }

    @Test
    void testToStringComPartesNulas() {
        FavoritadoJpa favoritado = new FavoritadoJpa();
        favoritado.setId(1);
        // prestador e cliente são nulos
        String expected = "FavoritadoJpa{id=1, prestadorId=null, clienteId=null}"; //
        assertEquals(expected, favoritado.toString());
    }
}