package com.exemple.implementacaoweb2.pedidos;


public interface PedidoRepository {

    Pedido save(Pedido pedido);
    void delete(int id);
    void update(int id);

}
