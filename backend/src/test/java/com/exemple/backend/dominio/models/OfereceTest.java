// Salve este arquivo como:
// src/test/java/com/exemple/backend/dominio/models/OfereceTest.java

package com.exemple.backend.dominio.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OfereceTest {

    private Prestador prestadorValido;
    private Servico servicoValido;

    @BeforeEach
    void setUp() {
        prestadorValido = new Prestador();
        servicoValido = new Servico();
    }

    @Test
    void deveConstruirOfereceComArgumentosValidos() {
        // Arrange: Passamos '1' como id para o construtor.
        Oferece oferece = new Oferece(1, prestadorValido, servicoValido);

        // Assert
        assertNotNull(oferece);

        assertEquals(0, oferece.getId(), "O ID da instância deve ser 0 se o construtor não o atribui.");

        assertSame(prestadorValido, oferece.getPrestador());
        assertSame(servicoValido, oferece.getServico());
    }

    @Test
    void deveLancarExcecaoNoConstrutorComPrestadorNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferece(1, null, servicoValido);
        });
        assertTrue(exception.getMessage().contains("Prestador não pode ser nulo"));
    }

    @Test
    void deveLancarExcecaoNoConstrutorComServicoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferece(1, prestadorValido, null);
        });
        assertTrue(exception.getMessage().contains("Serviço não pode ser nulo"));
    }

    @Test
    void testGettersAndSettersParaId() {
        Oferece oferece = new Oferece(); // Usa construtor padrão, id será 0
        assertEquals(0, oferece.getId(), "ID inicial do construtor padrão deve ser 0.");

        oferece.setId(10);
        assertEquals(10, oferece.getId(), "Getter de ID deve retornar o valor setado.");
    }

    @Test
    void testGettersAndSettersParaPrestadorEServico() {
        Oferece oferece = new Oferece();

        Prestador novoPrestador = new Prestador();
        oferece.setPrestador(novoPrestador);
        assertSame(novoPrestador, oferece.getPrestador());

        Servico novoServico = new Servico();
        oferece.setServico(novoServico);
        assertSame(novoServico, oferece.getServico());
    }

    @Test
    void testToString() {
        Prestador p = new Prestador();
        Servico s = new Servico();

        Oferece oferece = new Oferece(5, p, s);


        oferece.setId(5);


        String pStr = p.toString();
        String sStr = s.toString();
        String expected = String.format("Oferece{prestador=%s, servico=%s}", pStr, sStr);
        assertEquals(expected, oferece.toString());
    }

    @Test
    void testConstrutorPadraoInicializaIdComZero() {
        Oferece oferece = new Oferece();
        assertEquals(0, oferece.getId(), "ID deveria ser 0 quando o construtor padrão é usado.");
    }
}