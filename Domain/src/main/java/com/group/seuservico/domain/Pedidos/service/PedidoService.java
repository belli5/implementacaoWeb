package com.group.seuservico.domain.Pedidos.service;

import com.group.seuservico.domain.Pedidos.model.Pedido;
import com.group.seuservico.domain.Pedidos.repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido cadastrarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deletarPedido(int id) {
        pedidoRepository.delete(id);
    }

    public void atualizarPedido(int id) {
        pedidoRepository.update(id);
    }
    
}
