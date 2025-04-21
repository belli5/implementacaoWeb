package com.exemple.implementacaoweb2.pedidos;


import java.time.LocalDate;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido contratarServico(Pedido pedido) {
        if (!pedidoRepository.prestadorEstaDisponivel(pedido.getPrestadorId(), pedido.getData())) {
            throw new IllegalArgumentException("Prestador indisponível nesta data.");
        }
        return pedidoRepository.save(pedido);
    }

    public void deletarPedido(int id) {
        pedidoRepository.delete(id);
    }

    public void atualizarPedido(int id) {
        pedidoRepository.update(id);
    }


}

