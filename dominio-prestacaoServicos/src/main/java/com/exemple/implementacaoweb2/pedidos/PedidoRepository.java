package com.exemple.implementacaoweb2.pedidos;


import java.time.LocalDate;

public interface PedidoRepository {

    Pedido save(Pedido pedido);
    void delete(int id);
    void update(int id);
    boolean prestadorEstaDisponivel(int prestadorId, LocalDate data);


}
