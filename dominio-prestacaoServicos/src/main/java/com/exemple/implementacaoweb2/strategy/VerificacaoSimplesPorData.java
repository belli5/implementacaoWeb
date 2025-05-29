package com.exemple.implementacaoweb2.strategy;

import com.exemple.implementacaoweb2.pedidos.PedidoRepository;

import java.time.LocalDateTime;

public class VerificacaoSimplesPorData implements VerificacaoDisponibilidadeStrategy {

    private final PedidoRepository pedidoRepository;

    public VerificacaoSimplesPorData(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public boolean estaDisponivel(int prestadorId, LocalDateTime data) {
        return pedidoRepository.prestadorEstaDisponivel(prestadorId, data);
    }
}
