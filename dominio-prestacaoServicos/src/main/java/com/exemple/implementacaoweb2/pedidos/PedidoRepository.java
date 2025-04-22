package com.exemple.implementacaoweb2.pedidos;


import java.time.LocalDateTime;
import java.util.Optional;

public interface PedidoRepository {

    Pedido save(Pedido pedido);
    void delete(int id);
    void update(int id);
    boolean prestadorEstaDisponivel(int prestadorId, LocalDateTime data);


    Optional<Pedido> findById(int i);
}
