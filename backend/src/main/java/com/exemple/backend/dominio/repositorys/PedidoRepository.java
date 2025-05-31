package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository {
    public Optional<Pedido> findById(int id);
    public List<Pedido> findAll();
    public List<Pedido> findByPrestadorId(int prestadorId);
    public List<Pedido> findByClienteId(int clienteId);
    public Pedido save(Pedido pedido);
    public Pedido update(Pedido pedido);
    public void delete(int pedidoId);
}
