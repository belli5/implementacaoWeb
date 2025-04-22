package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.pedidos.Pedido;
import com.exemple.implementacaoweb2.pedidos.PedidoRepository;
import com.exemple.implementacaoweb2.pedidos.PedidoService;
import com.exemple.implementacaoweb2.pedidos.StatusPedido;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReagendarServicoTest {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        pedidoRepository = mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepository);

        // Configura um pedido concluído para teste
        PrestacaoServico servico = new PrestacaoServico(1, "Instalação elétrica", 250.0f, "Centro", "Eletricista", "Carlos Eletricista");

        Pedido pedidoConcluido = new Pedido(1, servico,123, 2, LocalDateTime.now().minusDays(5),StatusPedido.CONCLUIDO);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoConcluido));

        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testBuscarPedidoParaReagendar() {
        // Simula a busca de um pedido concluído para reagendar
        Optional<Pedido> pedido = pedidoService.buscarPorId(1L);

        assertTrue(pedido.isPresent());
        assertEquals(StatusPedido.CONCLUIDO, pedido.get().getStatus());
        assertEquals("Carlos Eletricista", pedido.get().getServico().getPrestador());
    }

    @Test
    public void testCriarNovoPedidoComMesmoServico() {
        LocalDateTime novaData = LocalDateTime.now().plusDays(3);

        // Simula a criação de um novo pedido com o mesmo serviço
        Pedido novoPedido = pedidoService.criarNovoPedido(1L, novaData);

        assertNotNull(novoPedido);
        assertEquals("Carlos Eletricista", novoPedido.getServico().getPrestador());
        assertEquals(novaData, novoPedido.getData());
        assertEquals(StatusPedido.PENDENTE, novoPedido.getStatus());

        // Verifica se o repositório foi chamado para salvar
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void testReagendarPedidoInexistente() {
        when(pedidoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.criarNovoPedido(99L, LocalDateTime.now());
        });
    }
}
