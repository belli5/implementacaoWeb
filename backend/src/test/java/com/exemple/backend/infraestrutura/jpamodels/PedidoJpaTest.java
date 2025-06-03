// src/test/java/com/exemple/backend/infraestrutura/jpamodels/PedidoJpaTest.java

package com.exemple.backend.infraestrutura.jpamodels;

import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PedidoJpaTest {

    @Test
    void testConstrutorPadrao() {
        PedidoJpa pedido = new PedidoJpa();
        assertNotNull(pedido, "Instância de PedidoJpa não deveria ser nula.");

        // CORREÇÃO: Se PedidoJpa.id é Integer e o construtor padrão o deixa null,
        // e getId() retorna Integer, então devemos esperar null.
        assertNull(pedido.getId(), "ID (como Integer) deveria ser nulo após construtor padrão.");

        assertNull(pedido.getData(), "Data deveria ser nula por padrão.");
        assertNull(pedido.getServico(), "Servico deveria ser nulo por padrão.");
        assertNull(pedido.getPrestador(), "Prestador deveria ser nulo por padrão.");
        assertNull(pedido.getCliente(), "Cliente deveria ser nulo por padrão.");
        assertNull(pedido.getStatus(), "Status deveria ser nulo por padrão.");
    }

    @Test
    void testConstrutorComArgumentos() {
        LocalDate data = LocalDate.of(2025, 6, 15);
        ServicoJpa servico = new ServicoJpa("S_Arg", "C_Arg", "D_Arg");
        // Para simplificar, EnderecoJpa pode ser nulo ou um objeto padrão
        // se não for o foco direto do teste de PedidoJpa.
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(101); // Supondo que PrestadorJpa tenha setId(int)
        ClienteJpa cliente = new ClienteJpa();
        cliente.setId(202);   // Supondo que ClienteJpa tenha setId(int)

        // Assumindo que o construtor de PedidoJpa aceita Integer para id:
        PedidoJpa pedido = new PedidoJpa(Integer.valueOf(1), data, servico, prestador, cliente, "ATIVO_ARG");

        assertEquals(Integer.valueOf(1), pedido.getId(), "ID não corresponde ao esperado.");
        assertEquals(data, pedido.getData(), "Data não corresponde à esperada.");
        assertSame(servico, pedido.getServico(), "Referência de Servico incorreta.");
        assertSame(prestador, pedido.getPrestador(), "Referência de Prestador incorreta.");
        assertSame(cliente, pedido.getCliente(), "Referência de Cliente incorreta.");
        assertEquals("ATIVO_ARG", pedido.getStatus(), "Status não corresponde ao esperado.");
    }

    @Test
    void testGettersAndSetters() {
        PedidoJpa pedido = new PedidoJpa();

        // Testando com Integer para id
        Integer idValue = Integer.valueOf(100);
        pedido.setId(idValue);
        assertEquals(idValue, pedido.getId(), "Getter de ID não retornou o valor setado.");

        LocalDate dataValue = LocalDate.now();
        pedido.setData(dataValue);
        assertEquals(dataValue, pedido.getData(), "Getter de Data não retornou o valor setado.");

        String statusValue = "CONCLUIDO_SET";
        pedido.setStatus(statusValue);
        assertEquals(statusValue, pedido.getStatus(), "Getter de Status não retornou o valor setado.");

        ServicoJpa servicoValue = new ServicoJpa();
        pedido.setServico(servicoValue);
        assertSame(servicoValue, pedido.getServico(), "Getter de Servico não retornou o objeto setado.");

        PrestadorJpa prestadorValue = new PrestadorJpa();
        pedido.setPrestador(prestadorValue);
        assertSame(prestadorValue, pedido.getPrestador(), "Getter de Prestador não retornou o objeto setado.");

        ClienteJpa clienteValue = new ClienteJpa();
        pedido.setCliente(clienteValue);
        assertSame(clienteValue, pedido.getCliente(), "Getter de Cliente não retornou o objeto setado.");
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

        PedidoJpa pedido = new PedidoJpa();
        pedido.setId(Integer.valueOf(50)); // id como Integer
        pedido.setData(data);
        pedido.setServico(servico);
        pedido.setPrestador(prestador);
        pedido.setCliente(cliente);
        pedido.setStatus("PENDENTE_TS");

        String dataStr = data.toString();
        String prestadorIdStr = (prestador.getId() == 0 && prestador.getNome() == null) ? "null" : String.valueOf(prestador.getId()); // Ajustado para o caso de getId() retornar int
        String clienteIdStr = (cliente.getId() == 0 && cliente.getNome() == null) ? "null" : String.valueOf(cliente.getId()); // Ajustado para o caso de getId() retornar int

        // O formato do toString em PedidoJpa usa %s para o ID do pedido se ele for Integer
        String expected = String.format(
                "PedidoJpa{id=%s, data=%s, status='PENDENTE_TS', servico='ServicoXYZ_TS', prestadorId=%s, clienteId=%s}",
                pedido.getId(),
                dataStr,
                prestadorIdStr,
                clienteIdStr
        );
        assertEquals(expected, pedido.toString(), "Formato do toString não corresponde.");
    }

    @Test
    void testToStringComPartesNulas() {
        PedidoJpa pedido = new PedidoJpa();
        LocalDate data = LocalDate.now();
        pedido.setId(Integer.valueOf(1)); // id como Integer
        pedido.setData(data);
        pedido.setStatus("INICIAL_TS");
        // servico, prestador, cliente são nulos por padrão na instanciação de PedidoJpa

        String dataStr = data.toString();
        String expected = String.format(
                "PedidoJpa{id=%s, data=%s, status='INICIAL_TS', servico='null', prestadorId=null, clienteId=null}",
                pedido.getId(), dataStr
        );
        assertEquals(expected, pedido.toString(), "Formato do toString com nulos não corresponde.");
    }
}