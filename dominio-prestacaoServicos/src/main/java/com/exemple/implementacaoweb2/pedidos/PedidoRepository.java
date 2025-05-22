package com.exemple.implementacaoweb2.pedidos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository {

    Pedido save(Pedido pedido);

    void delete(int id);

    void update(int id);

    Optional<Pedido> findById(int id);

    List<Pedido> findByClienteId(int clienteId);

    List<Pedido> findByPrestadorId(int prestadorId);

    List<Pedido> findByStatus(StatusPedido status);

    List<Pedido> findByData(LocalDateTime data);

    boolean prestadorEstaDisponivel(int prestadorId, LocalDateTime data);
}
