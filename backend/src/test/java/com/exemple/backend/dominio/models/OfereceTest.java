package com.exemple.backend.dominio.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OfereceTest {

    private Prestador prestadorValido;
    private Servico servicoValido;

    @BeforeEach
    void setUp() {
        // Mock ou instâncias simples, já que o construtor de Oferece não usa campos internos deles
        prestadorValido = new Prestador();
        servicoValido = new Servico();
    }

    @Test
    void deveConstruirOfereceComArgumentosValidos() {
        Oferece oferece = new Oferece(1, prestadorValido, servicoValido);
        assertNotNull(oferece);
        assertEquals(1, oferece.getId());
        assertSame(prestadorValido, oferece.getPrestador());
        assertSame(servicoValido, oferece.getServico());
    }

    @Test
    void deveLancarExcecaoNoConstrutorComPrestadorNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferece(1, null, servicoValido);
        });
        assertTrue(exception.getMessage().contains("Prestador não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComServicoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferece(1, prestadorValido, null);
        });
        assertTrue(exception.getMessage().contains("Serviço não pode ser nulo")); //
    }

    @Test
    void testGettersAndSetters() {
        Oferece oferece = new Oferece(); // Construtor padrão
        oferece.setId(10);
        Prestador novoPrestador = new Prestador();
        oferece.setPrestador(novoPrestador);
        Servico novoServico = new Servico();
        oferece.setServico(novoServico);

        assertEquals(10, oferece.getId());
        assertSame(novoPrestador, oferece.getPrestador());
        assertSame(novoServico, oferece.getServico());
    }

    @Test
    void testToString() {
        // O toString de Oferece usa o toString de Prestador e Servico.
        // Para um teste mais preciso do formato, você pode mockar o toString deles
        // ou usar instâncias com valores conhecidos que afetem seus toStrings.
        Prestador p = new Prestador(); // toString padrão do Object
        Servico s = new Servico();   // toString padrão do Object

        Oferece oferece = new Oferece(5, p, s);
        String pStr = p.toString();
        String sStr = s.toString();
        String expected = "Oferece{prestador=" + pStr + ", servico=" + sStr + "}"; //
        assertEquals(expected, oferece.toString());
    }
}