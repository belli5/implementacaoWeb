package org.example.persistence.repository.pedidos;

import org.example.model.pedidos.Pedido;

public interface PedidoRepository {

    Pedido save(Pedido pedido);
    void delete(int id);
    void update(int id);
    
}
