package com.exemple.backend.dominio.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FavoritadoTest {
    private Cliente clienteValido;
    private Prestador prestadorValido;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente();
        prestadorValido = new Prestador();
    }

    @Test
    void deveConstruirFavoritadoComArgumentosValidos() {
        Favoritado favoritado = new Favoritado(1, clienteValido, prestadorValido);
        assertNotNull(favoritado);
        assertEquals(1, favoritado.getId());
        assertSame(clienteValido, favoritado.getCliente());
        assertSame(prestadorValido, favoritado.getPrestador());
    }

    @Test
    void deveLancarExcecaoNoConstrutorComClienteNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Favoritado(1, null, prestadorValido);
        });
        assertTrue(exception.getMessage().contains("Cliente não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComPrestadorNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Favoritado(1, clienteValido, null);
        });
        assertTrue(exception.getMessage().contains("Prestador não pode ser nulo")); //
    }

    @Test
    void testGettersAndSetters() {
        Favoritado favoritado = new Favoritado(); // Construtor padrão
        favoritado.setId(20);
        Cliente novoCliente = new Cliente();
        favoritado.setCliente(novoCliente);
        Prestador novoPrestador = new Prestador();
        favoritado.setPrestador(novoPrestador);

        assertEquals(20, favoritado.getId());
        assertSame(novoCliente, favoritado.getCliente());
        assertSame(novoPrestador, favoritado.getPrestador());
    }

    @Test
    void testToString() {
        Cliente c = new Cliente();
        Prestador p = new Prestador();
        Favoritado favoritado = new Favoritado(7, c, p);
        String cStr = c.toString();
        String pStr = p.toString();
        String expected = "Favoritado{cliente=" + cStr + ", prestador=" + pStr + "}"; //
        assertEquals(expected, favoritado.toString());
    }
}
