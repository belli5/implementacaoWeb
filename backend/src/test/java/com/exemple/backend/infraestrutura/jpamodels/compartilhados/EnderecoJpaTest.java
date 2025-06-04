package com.exemple.backend.infraestrutura.jpamodels.compartilhados;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnderecoJpaTest {

    @Test
    void testConstrutorPadrao() {
        EnderecoJpa endereco = new EnderecoJpa();
        assertNotNull(endereco);
        assertNull(endereco.getRua(), "Rua deveria ser nula por padrão");
        assertNull(endereco.getBairro(), "Bairro deveria ser nulo por padrão");
        assertNull(endereco.getCidade(), "Cidade deveria ser nula por padrão");
        assertNull(endereco.getEstado(), "Estado deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        EnderecoJpa endereco = new EnderecoJpa("Rua Principal", "Centro", "Recife", "PE");
        assertEquals("Rua Principal", endereco.getRua());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("PE", endereco.getEstado());
    }

    @Test
    void testGettersAndSetters() {
        EnderecoJpa endereco = new EnderecoJpa();
        endereco.setRua("Rua Secundária");
        endereco.setBairro("Boa Viagem");
        endereco.setCidade("Olinda");
        endereco.setEstado("PE");

        assertEquals("Rua Secundária", endereco.getRua());
        assertEquals("Boa Viagem", endereco.getBairro());
        assertEquals("Olinda", endereco.getCidade());
        assertEquals("PE", endereco.getEstado());
    }

    @Test
    void testToString() {
        EnderecoJpa endereco = new EnderecoJpa("Av. Brasil", "Jardins", "São Paulo", "SP");
        String expected = "Endereço: rua=Av. Brasil, bairro=Jardins, cidade=São Paulo, estado=SP";
        assertEquals(expected, endereco.toString());
    }
}