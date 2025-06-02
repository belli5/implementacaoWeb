package com.exemple.backend.infraestrutura.jpamodels;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ServicoJpaTest {

    @Test
    void testConstrutorPadrao() {
        ServicoJpa servico = new ServicoJpa();
        assertNotNull(servico);
        assertNull(servico.getNome(), "Nome deveria ser nulo por padrão");
        assertNull(servico.getCategoria(), "Categoria deveria ser nula por padrão");
        assertNull(servico.getDescricao(), "Descrição deveria ser nula por padrão");
        assertNull(servico.getServicosOferecidos(), "ServicosOferecidos deveria ser nulo por padrão");
        assertNull(servico.getPedidosEnvolvidos(), "PedidosEnvolvidos deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        ServicoJpa servico = new ServicoJpa("Limpeza Profunda", "LIMPEZA", "Limpeza detalhada de ambientes.");
        assertEquals("Limpeza Profunda", servico.getNome());
        assertEquals("LIMPEZA", servico.getCategoria());
        assertEquals("Limpeza detalhada de ambientes.", servico.getDescricao());
        // Listas não são inicializadas neste construtor
        assertNull(servico.getServicosOferecidos());
        assertNull(servico.getPedidosEnvolvidos());
    }

    @Test
    void testGettersAndSetters() {
        ServicoJpa servico = new ServicoJpa();
        servico.setNome("Jardinagem Completa");
        servico.setCategoria("JARDIM");
        servico.setDescricao("Manutenção e paisagismo.");

        List<OfereceJpa> listaOferece = new ArrayList<>();
        OfereceJpa oferece1 = new OfereceJpa();
        listaOferece.add(oferece1);
        servico.setServicosOferecidos(listaOferece);

        List<PedidoJpa> listaPedidos = new ArrayList<>();
        PedidoJpa pedido1 = new PedidoJpa();
        listaPedidos.add(pedido1);
        servico.setPedidosEnvolvidos(listaPedidos);

        assertEquals("Jardinagem Completa", servico.getNome());
        assertEquals("JARDIM", servico.getCategoria());
        assertEquals("Manutenção e paisagismo.", servico.getDescricao());

        assertNotNull(servico.getServicosOferecidos());
        assertEquals(1, servico.getServicosOferecidos().size());
        assertSame(oferece1, servico.getServicosOferecidos().get(0));

        assertNotNull(servico.getPedidosEnvolvidos());
        assertEquals(1, servico.getPedidosEnvolvidos().size());
        assertSame(pedido1, servico.getPedidosEnvolvidos().get(0));
    }

    @Test
    void testToString() {
        ServicoJpa servico = new ServicoJpa("Consultoria TI", "TECNOLOGIA", "Consultoria especializada em sistemas.");
        String expected = "ServicoJpa{nome='Consultoria TI', categoria='TECNOLOGIA', descricao='Consultoria especializada em sistemas.'}"; //
        assertEquals(expected, servico.toString());
    }
}