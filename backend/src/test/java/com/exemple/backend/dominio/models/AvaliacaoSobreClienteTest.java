package com.exemple.backend.dominio.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobreClienteTest {

    private Prestador prestadorValido;
    private Cliente clienteValido;

    @BeforeEach
    void setUp() {
        prestadorValido = new Prestador();
        clienteValido = new Cliente();
    }

    @Test
    void deveConstruirAvaliacaoComArgumentosValidos() {
        AvaliacaoSobreCliente avaliacao = new AvaliacaoSobreCliente(1, prestadorValido, "Cliente atencioso.", 5, clienteValido);
        assertNotNull(avaliacao);
        assertEquals(1, avaliacao.getId());
        assertSame(prestadorValido, avaliacao.getPrestador());
        assertEquals("Cliente atencioso.", avaliacao.getComentario());
        assertEquals(5, avaliacao.getNota());
        assertSame(clienteValido, avaliacao.getCliente());
    }

    // ... Testes de exceção para construtor (prestador, comentario, cliente nulos) ...
    // Exemplo para prestador nulo:
    @Test
    void deveLancarExcecaoNoConstrutorComPrestadorNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AvaliacaoSobreCliente(1, null, "Comentário", 5, clienteValido);
        });
        assertTrue(exception.getMessage().contains("Prestador não pode ser nulo")); //
    }
    // ... Continue para comentario e cliente

    @Test
    void testGettersAndSetters() {
        AvaliacaoSobreCliente avaliacao = new AvaliacaoSobreCliente(); // Construtor padrão
        avaliacao.setId(15);
        Prestador novoPrestador = new Prestador();
        avaliacao.setPrestador(novoPrestador);
        avaliacao.setComentario("Pagamento rápido.");
        avaliacao.setNota(5);
        Cliente novoCliente = new Cliente();
        avaliacao.setCliente(novoCliente);

        assertEquals(15, avaliacao.getId());
        assertSame(novoPrestador, avaliacao.getPrestador());
        assertEquals("Pagamento rápido.", avaliacao.getComentario());
        assertEquals(5, avaliacao.getNota());
        assertSame(novoCliente, avaliacao.getCliente());
    }

    @Test
    void testToString() {
        // O toString de AvaliacaoSobreCliente usa getId() de Cliente e Prestador.
        // Para um teste preciso, precisamos que esses objetos tenham IDs.
        // Os objetos clienteValido e prestadorValido do setUp() são novos e não têm ID setado
        // (a menos que seus construtores padrão setassem, o que não é o caso).
        // Se eles fossem mockados ou tivessem setters para ID no modelo de domínio, poderíamos usar.
        // Vamos criar instâncias com IDs para o propósito do toString.

        Prestador p = new Prestador();
        p.setId(101); // Supondo que o modelo Prestador tenha setId ou o construtor o aceite. No seu caso, tem.
        Cliente c = new Cliente();
        c.setId(202); // Supondo que o modelo Cliente tenha setId. No seu caso, tem.

        AvaliacaoSobreCliente avaliacao = new AvaliacaoSobreCliente(7, p, "Comentário TS", 4, c);

        String expected = "AvaliacaoSobreCliente{id=7, nota=4, comentario='Comentário TS', cliente=202, prestador=101}"; //
        assertEquals(expected, avaliacao.toString());
    }
}