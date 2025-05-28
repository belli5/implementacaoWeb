package com.seuservico.infraestrutura.persistencia.jpa.pedidojpa;

import com.exemple.implementacaoweb2.pedidos.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoJpa, Integer> {

    List<PedidoJpa> findByCliente_Id(int clienteId);

    List<PedidoJpa> findByPrestador_Id(int prestadorId);

    List<PedidoJpa> findByStatus(StatusPedido status);

    List<PedidoJpa> findByData(LocalDateTime data);
}