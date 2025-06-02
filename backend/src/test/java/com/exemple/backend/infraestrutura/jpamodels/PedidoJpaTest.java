package com.exemple.backend.infraestrutura.jpamodels;

import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PedidoJpaTest {

    @Test
    void testConstrutorPadrao() {
        PedidoJpa pedido = new PedidoJpa();
        assertNotNull(pedido);
        assertEquals(0, pedido.getId(), "ID (int primitivo) deveria ser 0 por padrão");
        assertNull(pedido.getData(), "Data deveria ser nula por padrão");
        assertNull(pedido.getServico(), "Servico deveria ser nulo por padrão");
        assertNull(pedido.getPrestador(), "Prestador deveria ser nulo por padrão");
        assertNull(pedido.getCliente(), "Cliente deveria ser nulo por padrão");
        assertNull(pedido.getStatus(), "Status deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        LocalDate data = LocalDate.of(2025, 6, 15);
        ServicoJpa servico = new ServicoJpa("S_Arg", "C_Arg", "D_Arg");
        EnderecoJpa endereco = new EnderecoJpa();
        PrestadorJpa prestador = new PrestadorJpa("P_Arg", "sP", "eP", "tP", endereco);
        ClienteJpa cliente = new ClienteJpa("C_Arg", "sC", "eC", "tC", endereco);

        PedidoJpa pedido = new PedidoJpa(1, data, servico, prestador, cliente, "ATIVO_ARG");

        assertEquals(1, pedido.getId());
        assertEquals(data, pedido.getData());
        assertSame(servico, pedido.getServico());
        assertSame(prestador, pedido.getPrestador());
        assertSame(cliente, pedido.getCliente());
        assertEquals("ATIVO_ARG", pedido.getStatus());
    }

    @Test
    void testGettersAndSetters() {
        PedidoJpa pedido = new PedidoJpa();
        pedido.setId(100);
        LocalDate data = LocalDate.now();
        pedido.setData(data);
        pedido.setStatus("CONCLUIDO_SET");

        ServicoJpa servico = new ServicoJpa();
        pedido.setServico(servico);
        PrestadorJpa prestador = new PrestadorJpa();
        pedido.setPrestador(prestador);
        ClienteJpa cliente = new ClienteJpa();
        pedido.setCliente(cliente);

        assertEquals(100, pedido.getId());
        assertEquals(data, pedido.getData());
        assertEquals("CONCLUIDO_SET", pedido.getStatus());
        assertSame(servico, pedido.getServico());
        assertSame(prestador, pedido.getPrestador());
        assertSame(cliente, pedido.getCliente());
    }

    @Test
    void testToString() {
        LocalDate data = LocalDate.of(2025, 1, 10);
        ServicoJpa servico = new ServicoJpa();
        servico.setNome("ServicoXYZ_TS");
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(55);
        ClienteJpa cliente = new ClienteJpa();
        cliente.setId(88);

        PedidoJpa pedido = new PedidoJpa(50, data, servico, prestador, cliente, "PENDENTE_TS");

        String dataStr = data.toString();
        String expected = String.format(
                "PedidoJpa{id=50, data=%s, status='PENDENTE_TS', servico='ServicoXYZ_TS', prestadorId=55, clienteId=88}",
                dataStr
        ); //
        assertEquals(expected, pedido.toString());
    }

    @Test
    void testToStringComPartesNulas() {
        PedidoJpa pedido = new PedidoJpa();
        LocalDate data = LocalDate.now();
        pedido.setId(1);
        pedido.setData(data);
        pedido.setStatus("INICIAL_TS");

        String dataStr = data.toString();
        String expected = String.format(
                "PedidoJpa{id=1, data=%s, status='INICIAL_TS', servico='null', prestadorId=null, clienteId=null}",
                dataStr
        ); //
        assertEquals(expected, pedido.toString());
    }
}