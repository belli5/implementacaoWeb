package com.exemple.backend.infraestrutura.jpamodels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OfereceJpaTest {

    @Test
    void testConstrutorPadrao() {
        OfereceJpa oferece = new OfereceJpa();
        assertNotNull(oferece);
        assertEquals(0, oferece.getId(), "ID (int primitivo) deveria ser 0 por padrão");
        assertNull(oferece.getPrestador(), "Prestador deveria ser nulo por padrão");
        assertNull(oferece.getServico(), "Servico deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        PrestadorJpa prestador = new PrestadorJpa();
        ServicoJpa servico = new ServicoJpa();
        OfereceJpa oferece = new OfereceJpa(1, prestador, servico);

        assertEquals(1, oferece.getId());
        assertSame(prestador, oferece.getPrestador());
        assertSame(servico, oferece.getServico());
    }

    @Test
    void testGettersAndSetters() {
        OfereceJpa oferece = new OfereceJpa();
        oferece.setId(10);
        PrestadorJpa prestador = new PrestadorJpa();
        oferece.setPrestador(prestador);
        ServicoJpa servico = new ServicoJpa();
        oferece.setServico(servico);

        assertEquals(10, oferece.getId());
        assertSame(prestador, oferece.getPrestador());
        assertSame(servico, oferece.getServico());
    }

    @Test
    void testToString() {
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(77);
        ServicoJpa servico = new ServicoJpa();
        servico.setNome("NomeServicoTS");

        OfereceJpa oferece = new OfereceJpa(5, prestador, servico);
        String expected = "OfereceJpa{id=5, prestador=77, servico=NomeServicoTS}"; //
        assertEquals(expected, oferece.toString());
    }
}