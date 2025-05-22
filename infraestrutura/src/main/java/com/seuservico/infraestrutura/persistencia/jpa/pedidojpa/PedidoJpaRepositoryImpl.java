package com.seuservico.infraestrutura.persistencia.jpa.pedidojpa;

import com.exemple.implementacaoweb2.pedidos.Pedido;
import com.exemple.implementacaoweb2.pedidos.PedidoRepository;
import com.exemple.implementacaoweb2.pedidos.StatusPedido;
import com.seuservico.infraestrutura.persistencia.jpa.clientejpa.ClienteJpa;
import com.seuservico.infraestrutura.persistencia.jpa.clientejpa.ClienteJpaRepository;
import com.seuservico.infraestrutura.persistencia.jpa.MapperGeral;
import com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa.PrestacaoServicoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa.PrestacaoServicoJpaRepository;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PedidoJpaRepositoryImpl implements PedidoRepository {

    @Autowired
    private PedidoJpaRepository pedidoJpaRepository;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    @Autowired
    private PrestacaoServicoJpaRepository prestacaoServicoJpaRepository;

    @Override
    public Pedido save(Pedido pedido) {
        ClienteJpa clienteJpa = clienteJpaRepository.findById(pedido.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        PrestadorJpa prestadorJpa = prestadorJpaRepository.findById(pedido.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        PrestacaoServicoJpa servicoJpa = prestacaoServicoJpaRepository.findById(pedido.getServico().getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        PedidoJpa pedidoJpa = MapperGeral.toPedidoJpa(pedido, clienteJpa, prestadorJpa, servicoJpa);

        PedidoJpa saved = pedidoJpaRepository.save(pedidoJpa);

        return MapperGeral.toPedido(saved);
    }

    @Override
    public void delete(int id) {
        pedidoJpaRepository.deleteById(id);
    }

    @Override
    public void update(int id) {
        PedidoJpa pedidoJpa = pedidoJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Exemplo simples de atualização
        pedidoJpa.setStatus(StatusPedido.CONCLUIDO);

        pedidoJpaRepository.save(pedidoJpa);
    }

    @Override
    public Optional<Pedido> findById(int id) {
        return pedidoJpaRepository.findById(id)
                .map(MapperGeral::toPedido);
    }

    @Override
    public List<Pedido> findByClienteId(int clienteId) {
        return pedidoJpaRepository.findByCliente_Id(clienteId)
                .stream()
                .map(MapperGeral::toPedido)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findByPrestadorId(int prestadorId) {
        return pedidoJpaRepository.findByPrestador_Id(prestadorId)
                .stream()
                .map(MapperGeral::toPedido)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findByStatus(StatusPedido status) {
        return pedidoJpaRepository.findByStatus(status)
                .stream()
                .map(MapperGeral::toPedido)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findByData(LocalDateTime data) {
        return pedidoJpaRepository.findByData(data)
                .stream()
                .map(MapperGeral::toPedido)
                .collect(Collectors.toList());
    }

    @Override
    public boolean prestadorEstaDisponivel(int prestadorId, LocalDateTime data) {
        List<PedidoJpa> pedidos = pedidoJpaRepository.findByPrestador_Id(prestadorId);
        return pedidos.stream().noneMatch(p -> p.getData().isEqual(data));
    }
}
