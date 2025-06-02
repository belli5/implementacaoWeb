package com.exemple.backend.infraestrutura.jpamodels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobreClienteJpaTest {

    @Test
    void testConstrutorPadrao() {
        AvaliacaoSobreClienteJpa avaliacao = new AvaliacaoSobreClienteJpa();
        assertNotNull(avaliacao);
        assertEquals(0, avaliacao.getId(), "ID (int primitivo) deveria ser 0 por padrão");
        assertNull(avaliacao.getPrestador(), "Prestador deveria ser nulo por padrão");
        assertNull(avaliacao.getComentario(), "Comentario deveria ser nulo por padrão");
        assertEquals(0, avaliacao.getNota(), "Nota (int primitivo) deveria ser 0 por padrão");
        assertNull(avaliacao.getCliente(), "Cliente deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        PrestadorJpa prestador = new PrestadorJpa();
        ClienteJpa cliente = new ClienteJpa();
        AvaliacaoSobreClienteJpa avaliacao = new AvaliacaoSobreClienteJpa(1, prestador, "Cliente gente boa.", 4, cliente);

        assertEquals(1, avaliacao.getId());
        assertSame(prestador, avaliacao.getPrestador());
        assertEquals("Cliente gente boa.", avaliacao.getComentario());
        assertEquals(4, avaliacao.getNota());
        assertSame(cliente, avaliacao.getCliente());
    }

    @Test
    void testGettersAndSetters() {
        AvaliacaoSobreClienteJpa avaliacao = new AvaliacaoSobreClienteJpa();
        avaliacao.setId(35);
        PrestadorJpa prestador = new PrestadorJpa();
        avaliacao.setPrestador(prestador);
        avaliacao.setComentario("Pagamento em dia, muito bom.");
        avaliacao.setNota(5);
        ClienteJpa cliente = new ClienteJpa();
        avaliacao.setCliente(cliente);

        assertEquals(35, avaliacao.getId());
        assertSame(prestador, avaliacao.getPrestador());
        assertEquals("Pagamento em dia, muito bom.", avaliacao.getComentario());
        assertEquals(5, avaliacao.getNota());
        assertSame(cliente, avaliacao.getCliente());
    }

    @Test
    void testToString() {
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(444);
        ClienteJpa cliente = new ClienteJpa();
        cliente.setId(555);

        AvaliacaoSobreClienteJpa avaliacao = new AvaliacaoSobreClienteJpa(4, prestador, "Recomendo este cliente TS", 5, cliente);
        String expected = "AvaliacaoSobreClienteJpa{id=4, nota=5, comentario='Recomendo este cliente TS', clienteId=555, prestadorId=444}"; //
        assertEquals(expected, avaliacao.toString());
    }

    @Test
    void testToStringComPartesNulas() {
        AvaliacaoSobreClienteJpa avaliacao = new AvaliacaoSobreClienteJpa();
        avaliacao.setId(1);
        avaliacao.setComentario("Comentário TS");
        avaliacao.setNota(2);
        // prestador e cliente são nulos
        String expected = "AvaliacaoSobreClienteJpa{id=1, nota=2, comentario='Comentário TS', clienteId=null, prestadorId=null}"; //
        assertEquals(expected, avaliacao.toString());
    }
}