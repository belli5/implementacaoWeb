package com.exemple.backend.dominio.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoTest {

    @Test
    void deveConstruirServicoComArgumentosValidos() {
        Servico servico = new Servico("Limpeza Geral", "LIMPEZA", "Serviço completo de limpeza.");
        assertNotNull(servico);
        assertEquals("Limpeza Geral", servico.getNome());
        assertEquals("LIMPEZA", servico.getCategoria());
        assertEquals("Serviço completo de limpeza.", servico.getDescricao());
    }

    @Test
    void testConstrutorPadrao() {
        Servico servico = new Servico();
        assertNotNull(servico);
        assertNull(servico.getNome()); // Atributos não inicializados serão null
        assertNull(servico.getCategoria());
        assertNull(servico.getDescricao());
    }

    @Test
    void deveLancarExcecaoNoConstrutorComNomeNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Servico(null, "CATEGORIA", "Descrição válida");
        });
        assertTrue(exception.getMessage().contains("Nome do serviço não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComCategoriaNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Servico("Nome Válido", null, "Descrição válida");
        });
        assertTrue(exception.getMessage().contains("Categoria do serviço não pode ser nula")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComDescricaoNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Servico("Nome Válido", "CATEGORIA", null);
        });
        assertTrue(exception.getMessage().contains("Descrição do serviço não pode ser nula")); //
    }

    @Test
    void testGettersAndSetters() {
        Servico servico = new Servico(); // Usa construtor padrão
        servico.setNome("Jardinagem");
        servico.setCategoria("EXTERNO");
        servico.setDescricao("Corte de grama e poda.");

        assertEquals("Jardinagem", servico.getNome());
        assertEquals("EXTERNO", servico.getCategoria());
        assertEquals("Corte de grama e poda.", servico.getDescricao());
    }

    @Test
    void testToString() {
        Servico servico = new Servico("Pintura Residencial", "PINTURA", "Pintura interna e externa.");
        String expected = "Servico{nome='Pintura Residencial', categoria='PINTURA', descricao='Pintura interna e externa.'}"; //
        assertEquals(expected, servico.toString());
    }
}