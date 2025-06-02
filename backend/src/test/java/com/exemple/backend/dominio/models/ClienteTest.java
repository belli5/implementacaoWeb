package com.exemple.backend.dominio.models;

import com.exemple.backend.dominio.models.compartilhados.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Endereco enderecoValido;

    @BeforeEach
    void setUp() {
        enderecoValido = new Endereco("Rua Cliente", "Bairro Cli", "Cidade Cli", "CL");
    }

    @Test
    void deveConstruirClienteComArgumentosValidos() {
        Cliente cliente = new Cliente(1, "João Silva", "senha123", "joao@email.com", "999887766", enderecoValido);
        assertNotNull(cliente);
        assertEquals(Integer.valueOf(1), cliente.getId());
        assertEquals("João Silva", cliente.getNome());
        assertEquals("senha123", cliente.getSenha());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("999887766", cliente.getTelefone());
        assertSame(enderecoValido, cliente.getEndereco());
    }

    @Test
    void testConstrutorPadrao() {
        Cliente cliente = new Cliente();
        assertNotNull(cliente);
        assertNull(cliente.getId());
        assertNull(cliente.getNome());
        // ... demais campos
    }

    @Test
    void deveLancarExcecaoNoConstrutorComIdNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, "Nome", "senha", "email", "tel", enderecoValido);
        });
        assertTrue(exception.getMessage().contains("Id do usuário não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComNomeNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(1, null, "senha", "email", "tel", enderecoValido);
        });
        assertTrue(exception.getMessage().contains("Nome do usuário não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComSenhaNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(1, "Nome", null, "email", "tel", enderecoValido);
        });
        assertTrue(exception.getMessage().contains("Senha do usuário não pode ser nula")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComEmailNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(1, "Nome", "senha", null, "tel", enderecoValido);
        });
        assertTrue(exception.getMessage().contains("Email do usuário não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComTelefoneNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(1, "Nome", "senha", "email", null, enderecoValido);
        });
        assertTrue(exception.getMessage().contains("Telefone do usuário não pode ser nulo")); //
    }

    @Test
    void deveLancarExcecaoNoConstrutorComEnderecoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(1, "Nome", "senha", "email", "tel", null);
        });
        assertTrue(exception.getMessage().contains("Endereço do usuário não pode ser nulo")); //
    }


    @Test
    void testGettersAndSetters() {
        Cliente cliente = new Cliente();
        cliente.setId(10);
        cliente.setNome("Maria Teste");
        cliente.setSenha("mariaSenha");
        cliente.setEmail("maria@teste.com");
        cliente.setTelefone("111222333");
        Endereco novoEndereco = new Endereco("Nova Rua", "Novo Bairro", "Nova Cidade", "NV");
        cliente.setEndereco(novoEndereco);

        assertEquals(Integer.valueOf(10), cliente.getId());
        assertEquals("Maria Teste", cliente.getNome());
        assertEquals("mariaSenha", cliente.getSenha());
        assertEquals("maria@teste.com", cliente.getEmail());
        assertEquals("111222333", cliente.getTelefone());
        assertSame(novoEndereco, cliente.getEndereco());
    }

    @Test
    void testToString() {
        Cliente cliente = new Cliente(5, "Carlos Teste String", "strSenha", "carlos@str.com", "777888999", enderecoValido);
        String enderecoStr = enderecoValido.toString();
        String expected = "Cliente{id=5, nome='Carlos Teste String', senha='strSenha', email='carlos@str.com', telefone='777888999', endereco=" + enderecoStr + "}"; //
        assertEquals(expected, cliente.toString());
    }
}