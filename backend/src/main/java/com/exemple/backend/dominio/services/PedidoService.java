package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.dominio.repositorys.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public Optional<Pedido> buscarPorId(int id){
        return pedidoRepository.findById(id);
    }

    public List<Pedido> buscarTodos(){
        return pedidoRepository.findAll();
    }

    public List<Pedido> buscarPorPrestador(int prestadorId){
        return pedidoRepository.findByPrestadorId(prestadorId);
    }

    public List<Pedido> buscarPorCliente(int clienteId){
        return pedidoRepository.findByClienteId(clienteId);
    }

    public Pedido salvar(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar (Pedido pedido){
        return pedidoRepository.update(pedido);
    }

    public void deletar(int id){
        pedidoRepository.delete(id);
    }
}
