package com.exemple.backend.infraestrutura.jpamodels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobrePrestadorJpaTest {

    @Test
    void testConstrutorPadrao() {
        AvaliacaoSobrePrestadorJpa avaliacao = new AvaliacaoSobrePrestadorJpa();
        assertNotNull(avaliacao);
        assertEquals(0, avaliacao.getId(), "ID (int primitivo) deveria ser 0 por padrão");
        assertNull(avaliacao.getCliente(), "Cliente deveria ser nulo por padrão");
        assertNull(avaliacao.getComentario(), "Comentario deveria ser nulo por padrão");
        assertEquals(0, avaliacao.getNota(), "Nota (int primitivo) deveria ser 0 por padrão");
        assertNull(avaliacao.getPrestador(), "Prestador deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        ClienteJpa cliente = new ClienteJpa();
        PrestadorJpa prestador = new PrestadorJpa();
        AvaliacaoSobrePrestadorJpa avaliacao = new AvaliacaoSobrePrestadorJpa(1, cliente, "Excelente trabalho!", 5, prestador);

        assertEquals(1, avaliacao.getId());
        assertSame(cliente, avaliacao.getCliente());
        assertEquals("Excelente trabalho!", avaliacao.getComentario());
        assertEquals(5, avaliacao.getNota());
        assertSame(prestador, avaliacao.getPrestador());
    }

    @Test
    void testGettersAndSetters() {
        AvaliacaoSobrePrestadorJpa avaliacao = new AvaliacaoSobrePrestadorJpa();
        avaliacao.setId(25);
        ClienteJpa cliente = new ClienteJpa();
        avaliacao.setCliente(cliente);
        avaliacao.setComentario("Razoável, mas atrasou.");
        avaliacao.setNota(3);
        PrestadorJpa prestador = new PrestadorJpa();
        avaliacao.setPrestador(prestador);

        assertEquals(25, avaliacao.getId());
        assertSame(cliente, avaliacao.getCliente());
        assertEquals("Razoável, mas atrasou.", avaliacao.getComentario());
        assertEquals(3, avaliacao.getNota());
        assertSame(prestador, avaliacao.getPrestador());
    }

    @Test
    void testToString() {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setId(111);
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(222);

        AvaliacaoSobrePrestadorJpa avaliacao = new AvaliacaoSobrePrestadorJpa(3, cliente, "Muito bom profissional TS", 5, prestador);
        String expected = "AvaliacaoSobrePrestadorJpa{id=3, nota=5, comentario='Muito bom profissional TS', clienteId=111, prestadorId=222}"; //
        assertEquals(expected, avaliacao.toString());
    }

    @Test
    void testToStringComPartesNulas() {
        AvaliacaoSobrePrestadorJpa avaliacao = new AvaliacaoSobrePrestadorJpa();
        avaliacao.setId(1);
        avaliacao.setComentario("Teste TS");
        avaliacao.setNota(1);
        // cliente e prestador são nulos
        String expected = "AvaliacaoSobrePrestadorJpa{id=1, nota=1, comentario='Teste TS', clienteId=null, prestadorId=null}"; //
        assertEquals(expected, avaliacao.toString());
    }
}