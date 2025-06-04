package com.exemple.backend.dominio.models;

import com.exemple.backend.dominio.models.compartilhados.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Cliente clienteValido;
    private Prestador prestadorValido;
    private Servico servicoValido;
    private Endereco enderecoComum;

    @BeforeEach
    void setUp() {
        enderecoComum = new Endereco("Rua Comum Ped", "Bairro CP", "Cidade CP", "CP");
        clienteValido = new Cliente(1, "Cli Ped", "s", "c@p.com", "t", enderecoComum);
        prestadorValido = new Prestador(1, "Pre Ped", "s", "p@p.com", "t", enderecoComum);
        servicoValido = new Servico("Serv Ped", "CATP", "Desc P");
    }

    @Test
    void deveConstruirPedidoComArgumentosValidos() {
        LocalDate data = LocalDate.now();
        Pedido pedido = new Pedido(1, data, servicoValido, prestadorValido, clienteValido, "PENDENTE");
        assertNotNull(pedido);
        assertEquals(Integer.valueOf(1), pedido.getId());
        assertEquals(data, pedido.getData());
        assertSame(servicoValido, pedido.getServico());
        assertSame(prestadorValido, pedido.getPrestador());
        assertSame(clienteValido, pedido.getCliente());
        assertEquals("PENDENTE", pedido.getStatus());
    }

    // ... Testes de exceção para construtor (id, data, servico, prestador, cliente, status nulos) ...
    // Exemplo para ID nulo:
    @Test
    void deveLancarExcecaoNoConstrutorComIdNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pedido(null, LocalDate.now(), servicoValido, prestadorValido, clienteValido, "STATUS");
        });
        assertTrue(exception.getMessage().contains("Id do pedido não pode ser nulo")); //
    }
    // ... Continue para os outros campos do construtor

    @Test
    void testGettersAndSetters() {
        Pedido pedido = new Pedido();
        pedido.setId(10);
        LocalDate data = LocalDate.of(2025, 10, 1);
        pedido.setData(data);
        pedido.setStatus("CONCLUIDO");
        pedido.setServico(servicoValido);
        pedido.setPrestador(prestadorValido);
        pedido.setCliente(clienteValido);

        assertEquals(Integer.valueOf(10), pedido.getId());
        assertEquals(data, pedido.getData());
        assertEquals("CONCLUIDO", pedido.getStatus());
        assertSame(servicoValido, pedido.getServico());
        assertSame(prestadorValido, pedido.getPrestador());
        assertSame(clienteValido, pedido.getCliente());
    }

    @Test
    void testToString() {
        LocalDate data = LocalDate.of(2025, 12, 25);
        Pedido pedido = new Pedido(50, data, servicoValido, prestadorValido, clienteValido, "EM_ANDAMENTO");
        String servicoStr = servicoValido.toString();
        String prestadorStr = prestadorValido.toString();
        String clienteStr = clienteValido.toString();
        String expected = "Pedido{id=50, data=" + data + ", status='EM_ANDAMENTO', servico=" + servicoStr + ", prestador=" + prestadorStr + ", cliente=" + clienteStr + "}"; //
        assertEquals(expected, pedido.toString());
    }
}