package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.dominio.repositorys.PedidoRepository;
import com.exemple.backend.infraestrutura.jpamodels.PedidoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.PedidoJpaRepository;
import com.exemple.backend.infraestrutura.mappers.ClienteMapper;
import com.exemple.backend.infraestrutura.mappers.PedidoMapper;
import com.exemple.backend.infraestrutura.mappers.PrestadorMapper;
import com.exemple.backend.infraestrutura.mappers.ServicoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final PedidoJpaRepository pedidoJpaRepository;

    public PedidoRepositoryImpl(PedidoJpaRepository pedidoJpaRepository) {
        this.pedidoJpaRepository = pedidoJpaRepository;
    }

    @Override
    public Optional<Pedido> findById(int id){
        return pedidoJpaRepository.findById(id)
                .map(PedidoMapper::toPedido);
    }

    @Override
    public List<Pedido> findAll(){
        return pedidoJpaRepository.findAll()
                .stream()
                .map(PedidoMapper::toPedido)
                .toList();
    }

    @Override
    public List<Pedido> findByPrestadorId(int prestador_Id) {
        return pedidoJpaRepository.findByPrestadorId(prestador_Id)
                .stream()
                .map(PedidoMapper::toPedido)
                .toList();
    }

    @Override
    public List<Pedido> findByClienteId(int cliente_Id) {
        return pedidoJpaRepository.findByClienteId(cliente_Id)
                .stream()
                .map(PedidoMapper::toPedido)
                .toList();
    }


    @Override
    public Pedido save(Pedido pedido){
        notNull(pedido, "Pedido não deve ser nulo");

        PedidoJpa pedidoJpa = PedidoMapper.toPedidoJpa(pedido);
        return PedidoMapper.toPedido(pedidoJpaRepository.save(pedidoJpa));
    }

    @Override
    public Pedido update(Pedido pedido){
        notNull(pedido, "Pedido não deve ser nulo");

        PedidoJpa exists = pedidoJpaRepository.findById(pedido.getId()).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + pedido.getId()));

        exists.setData(pedido.getData());
        exists.setServico(ServicoMapper.toServicoJpa(pedido.getServico()));
        exists.setPrestador(PrestadorMapper.toPrestadorJpa(pedido.getPrestador()));
        exists.setCliente(ClienteMapper.toClienteJpa(pedido.getCliente()));
        exists.setStatus(pedido.getStatus());

        return PedidoMapper.toPedido(pedidoJpaRepository.save(exists));
    }

    @Override
    public void delete(int Id){
        pedidoJpaRepository.deleteById(Id);
    }
}
