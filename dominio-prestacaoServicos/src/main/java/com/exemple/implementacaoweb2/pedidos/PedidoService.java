package com.exemple.implementacaoweb2.pedidos;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id.intValue());
    }

    public Pedido criarNovoPedido(Long idPedidoOriginal, LocalDateTime novaData) {
        Pedido pedidoOriginal = pedidoRepository.findById(idPedidoOriginal.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        int prestadorId = pedidoOriginal.getPrestadorId();

        if (!pedidoRepository.prestadorEstaDisponivel(prestadorId, novaData)) {
            throw new IllegalArgumentException("Prestador indisponível nesta data.");
        }

        Pedido novoPedido = new Pedido(
                0,
                pedidoOriginal.getServico(),
                pedidoOriginal.getPrestadorId(),
                pedidoOriginal.getClienteId(),
                novaData,
                StatusPedido.PENDENTE
        );

        return pedidoRepository.save(novoPedido);
    }
}
