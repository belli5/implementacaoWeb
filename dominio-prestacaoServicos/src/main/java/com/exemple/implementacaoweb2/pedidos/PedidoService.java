package com.exemple.implementacaoweb2.pedidos;

import com.exemple.implementacaoweb2.strategy.VerificacaoDisponibilidadeStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private VerificacaoDisponibilidadeStrategy verificacaoDisponibilidade;

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

    public List<Pedido> buscarPorPrestadorId(int prestadorId) {
        return pedidoRepository.findByPrestadorId(prestadorId);
    }

    public List<Pedido> buscarPorClienteId(int clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public VerificacaoDisponibilidadeStrategy setVerificacaoDisponibilidade(VerificacaoDisponibilidadeStrategy strategy) {
        return this.verificacaoDisponibilidade = strategy;
    }
}
