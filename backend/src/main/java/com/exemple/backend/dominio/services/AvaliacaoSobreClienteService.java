package com.exemple.backend.dominio.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.exemple.backend.dominio.repositorys.AvaliacaoSobreClienteRepository;
import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;

public class AvaliacaoSobreClienteService {

    private final AvaliacaoSobreClienteRepository avaliacaoSobreClienteRepository;


    public AvaliacaoSobreClienteService(AvaliacaoSobreClienteRepository avaliacaoSobreClienteRepository) {
        this.avaliacaoSobreClienteRepository = avaliacaoSobreClienteRepository;
    }

    public List<AvaliacaoSobreCliente> contarAvaliacoesPorCliente(int clienteId){
        return avaliacaoSobreClienteRepository.findByClienteId(clienteId);
    }

    public Optional<AvaliacaoSobreCliente> buscarPorId(int id) {
        return avaliacaoSobreClienteRepository.findById(id);
    }

    public AvaliacaoSobreCliente salvarAvaliacao(AvaliacaoSobreCliente avaliacao) {
        return avaliacaoSobreClienteRepository.save(avaliacao);
    }

    public void deletarAvaliacao(int id) {
        avaliacaoSobreClienteRepository.delete(id);
    }
}
