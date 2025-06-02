package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.infraestrutura.jpamodels.PedidoJpa;

import static org.springframework.util.Assert.notNull;

public class PedidoMapper {

    public static PedidoJpa toPedidoJpa(Pedido pedido) {
        notNull(pedido, "Pedido não pode ser nulo");

        PedidoJpa jpa = new PedidoJpa();
        jpa.setData(pedido.getData());
        jpa.setServico(ServicoMapper.toServicoJpa(pedido.getServico()));
        jpa.setPrestador(PrestadorMapper.toPrestadorJpa(pedido.getPrestador()));
        jpa.setCliente(ClienteMapper.toClienteJpa(pedido.getCliente()));
        jpa.setStatus(pedido.getStatus());

        return jpa;
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
