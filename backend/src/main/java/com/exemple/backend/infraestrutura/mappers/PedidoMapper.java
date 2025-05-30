package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.infraestrutura.jpamodels.PedidoJpa;

import static org.springframework.util.Assert.notNull;

public class PedidoMapper {

    public static PedidoJpa toPedidoJpa(Pedido pedido) {
        notNull(pedido, "Pedido não pode ser nulo");

        return new PedidoJpa(
                pedido.getId(),
                pedido.getData(),
                ServicoMapper.toServicoJpa(pedido.getServico()),
                PrestadorMapper.toPrestadorJpa(pedido.getPrestador()),
                ClienteMapper.toClienteJpa(pedido.getCliente()),
                pedido.getStatus()
        );
    }

    public static Pedido toPedido(PedidoJpa pedidoJpa) {
        notNull(pedidoJpa, "PedidoJpa não pode ser nulo");

        return new Pedido(
                pedidoJpa.getId(),
                pedidoJpa.getData(),
                ServicoMapper.toServico(pedidoJpa.getServico()),
                PrestadorMapper.toPrestador(pedidoJpa.getPrestador()),
                ClienteMapper.toCliente(pedidoJpa.getCliente()),
                pedidoJpa.getStatus()
        );
    }
}
