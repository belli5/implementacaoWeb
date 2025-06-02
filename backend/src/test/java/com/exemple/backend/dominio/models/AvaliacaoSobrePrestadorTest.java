package com.exemple.backend.dominio.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobrePrestadorTest {

    private Cliente clienteValido;
    private Prestador prestadorValido;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente();
        prestadorValido = new Prestador();
    }

    @Test
    void deveConstruirAvaliacaoComArgumentosValidos() {
        AvaliacaoSobrePrestador avaliacao = new AvaliacaoSobrePrestador(1, clienteValido, "Excelente!", 5, prestadorValido);
        assertNotNull(avaliacao);
        assertEquals(Integer.valueOf(1), avaliacao.getId());
        assertSame(clienteValido, avaliacao.getCliente());
        assertEquals("Excelente!", avaliacao.getComentario());
        assertEquals(Integer.valueOf(5), avaliacao.getNota());
        assertSame(prestadorValido, avaliacao.getPrestador());
    }

    // ... Testes de exceção para construtor (id, cliente, comentario, nota, prestador nulos) ...
    // Exemplo para id nulo:
    @Test
    void deveLancarExcecaoNoConstrutorComIdNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AvaliacaoSobrePrestador(null, clienteValido, "Comentário", 5, prestadorValido);
        });
        assertTrue(exception.getMessage().contains("Id da avaliação não pode ser nulo")); //
    }
    // ... Continue para os outros campos

    @Test
    void testGettersAndSetters() {
        AvaliacaoSobrePrestador avaliacao = new AvaliacaoSobrePrestador(); // Construtor padrão
        avaliacao.setId(10);
        Cliente novoCliente = new Cliente();
        avaliacao.setCliente(novoCliente);
        avaliacao.setComentario("Bom serviço.");
        avaliacao.setNota(4);
        Prestador novoPrestador = new Prestador();
        avaliacao.setPrestador(novoPrestador);

        assertEquals(Integer.valueOf(10), avaliacao.getId());
        assertSame(novoCliente, avaliacao.getCliente());
        assertEquals("Bom serviço.", avaliacao.getComentario());
        assertEquals(Integer.valueOf(4), avaliacao.getNota());
        assertSame(novoPrestador, avaliacao.getPrestador());
    }

    // A classe AvaliacaoSobrePrestador não possui método toString() customizado.
    // Se tivesse, seria testado aqui.
}