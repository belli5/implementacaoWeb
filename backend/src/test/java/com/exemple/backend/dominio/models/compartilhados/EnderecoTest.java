package com.exemple.backend.dominio.models.compartilhados;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    @Test
    void deveConstruirEnderecoComArgumentosValidos() {
        Endereco endereco = new Endereco("Rua Principal", "Centro", "Cidade Exemplo", "EX");
        assertNotNull(endereco);
        assertEquals("Rua Principal", endereco.getRua());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("Cidade Exemplo", endereco.getCidade());
        assertEquals("EX", endereco.getEstado());
    }

    // A classe Endereco não possui validação Assert.notNull em seu construtor,
    // e seus campos são 'final', então não há setters.
    // Se fosse necessário validar nulidade, seria no código que *usa* Endereco.

    @Test
    void deveRetornarValoresCorretosNosGetters() {
        String rua = "Rua Teste Getters";
        String bairro = "Bairro Getters";
        String cidade = "Cidade Getters";
        String estado = "GT";
        Endereco endereco = new Endereco(rua, bairro, cidade, estado);

        assertEquals(rua, endereco.getRua());
        assertEquals(bairro, endereco.getBairro());
        assertEquals(cidade, endereco.getCidade());
        assertEquals(estado, endereco.getEstado());
    }

    @Test
    void testToString() {
        Endereco endereco = new Endereco("Av. Teste", "Jardim Teste", "Testeópolis", "TS");
        // Conforme a implementação em backend/src/main/java/com/exemple/backend/dominio/models/compartilhados/Endereco.java
        String expected = "Endereço:rua:Av. Teste, bairro:Jardim Teste, cidade:Testeópolis, estado:TS";
        assertEquals(expected, endereco.toString());
    }
}