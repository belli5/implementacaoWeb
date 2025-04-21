package com.group.seuservico.domain.Pedidos.repository;

import com.group.seuservico.domain.Pedidos.model.Pedido;

public interface PedidoRepository {

    Pedido save(Pedido pedido);
    void delete(int id);
    void update(int id);
    
}
