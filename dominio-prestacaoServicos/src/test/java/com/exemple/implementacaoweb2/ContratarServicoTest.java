package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.pedidos.Pedido;
import com.exemple.implementacaoweb2.pedidos.PedidoRepository;
import com.exemple.implementacaoweb2.pedidos.PedidoService;
import com.exemple.implementacaoweb2.pedidos.StatusPedido;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContratarServicoTest {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        pedidoRepository = Mockito.mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepository);

    }


    @Test
    public void testClienteContrataPrestadorComSucesso() {
        PrestacaoServico servico = new PrestacaoServico(1, "Instalação elétrica", 250.0f, "Centro", "Eletricista", "Carlos Eletricista");

        // Dado
        Pedido pedido = new Pedido(1, servico, 101, 202, LocalDate.of(2025, 4, 25).atStartOfDay(), StatusPedido.PENDENTE);

        // E: o prestador está disponível
        when(pedidoRepository.prestadorEstaDisponivel(101, LocalDate.of(2025, 4, 25).atStartOfDay())).thenReturn(true);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        // Quando
        Pedido resultado = pedidoService.contratarServico(pedido);

        // Então
        assertNotNull(resultado);
        assertEquals(pedido.getId(), resultado.getId());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void testClienteTentaContratarPrestadorIndisponivel() {
        PrestacaoServico servico = new PrestacaoServico(1, "Instalação elétrica", 250.0f, "Centro", "Eletricista", "Carlos Eletricista");

        // Dado
        Pedido pedido = new Pedido(2, servico,102, 203, LocalDate.of(2025, 4, 25).atStartOfDay(), StatusPedido.PENDENTE);

        // E: o prestador está indisponível
        when(pedidoRepository.prestadorEstaDisponivel(102, LocalDate.of(2025, 4, 25).atStartOfDay())).thenReturn(false);

        // Quando
        try {
            pedidoService.contratarServico(pedido);
            fail("Esperava exceção de prestador indisponível.");
        } catch (IllegalArgumentException e) {
            // Então:
            assertEquals("Prestador indisponível nesta data.", e.getMessage());
        }

        verify(pedidoRepository, never()).save(any());
    }
}
