package com.exemple.backend.dominio.models;

import com.exemple.backend.dominio.models.compartilhados.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrestadorTest {

    private Endereco enderecoValido;

    @BeforeEach
    void setUp() {
        enderecoValido = new Endereco("Rua Prestador", "Bairro Prest", "Cidade Prest", "PR");
    }

    @Test
    void deveConstruirPrestadorComArgumentosValidos() {
        Prestador prestador = new Prestador(1, "Ana Serviços", "ana123", "ana@serv.com", "123456789", enderecoValido);
        assertNotNull(prestador);
        assertEquals(Integer.valueOf(1), prestador.getId());
        assertEquals("Ana Serviços", prestador.getNome());
        assertEquals("ana123", prestador.getSenha());
        assertEquals("ana@serv.com", prestador.getEmail());
        assertEquals("123456789", prestador.getTelefone());
        assertSame(enderecoValido, prestador.getEndereco());
    }

    @Test
    void testConstrutorPadrao() {
        Prestador prestador = new Prestador();
        assertNotNull(prestador);
        assertNull(prestador.getId());
        // ...
    }

    @Test
    void deveLancarExcecaoNoConstrutorComIdNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Prestador(null, "Nome", "senha", "email", "tel", enderecoValido);
        });
        assertTrue(exception.getMessage().contains("Id do prestador não pode ser nulo")); //
    }

    // ... Outros testes de exceção para construtor (nome, senha, email, telefone, endereco nulos) ...
    // Similar ao ClienteTest

    @Test
    void testGettersAndSetters() {
        Prestador prestador = new Prestador();
        prestador.setId(20);
        prestador.setNome("Pedro Manutenção");
        prestador.setSenha("pedroM");
        prestador.setEmail("pedro@manut.com");
        prestador.setTelefone("987654321");
        Endereco novoEndereco = new Endereco("Rua Manut", "Bairro M", "Cidade M", "MN");
        prestador.setEndereco(novoEndereco);

        assertEquals(Integer.valueOf(20), prestador.getId());
        assertEquals("Pedro Manutenção", prestador.getNome());
        assertEquals("pedroM", prestador.getSenha());
        assertEquals("pedro@manut.com", prestador.getEmail());
        assertEquals("987654321", prestador.getTelefone());
        assertSame(novoEndereco, prestador.getEndereco());
    }

    @Test
    void testToString() {
        Prestador prestador = new Prestador(7, "Empresa XYZ", "xyzPass", "contato@xyz.com", "000111222", enderecoValido);
        String enderecoStr = enderecoValido.toString();
        String expected = "Prestador{id=7, nome='Empresa XYZ', senha='xyzPass', email='contato@xyz.com', telefone='000111222', endereco=" + enderecoStr + "}"; //
        assertEquals(expected, prestador.toString());
    }
}