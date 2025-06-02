package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobreClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoSobreClienteService{

    private final AvaliacaoSobreClienteRepository avaliacaoSobreClienteRepository;

    public AvaliacaoSobreClienteService(AvaliacaoSobreClienteRepository avaliacaoSobreClienteRepository) {
        this.avaliacaoSobreClienteRepository = avaliacaoSobreClienteRepository;
    }

    public Optional<AvaliacaoSobreCliente> findById(int id) {
        return avaliacaoSobreClienteRepository.findById(id);
    }

    public List<AvaliacaoSobreCliente> findAll() {
        return avaliacaoSobreClienteRepository.findAll();
    }

    public List<AvaliacaoSobreCliente> findByClienteId(int clienteId) {
        return avaliacaoSobreClienteRepository.findByClienteId(clienteId);
    }

    public AvaliacaoSobreCliente save(AvaliacaoSobreCliente avaliacao) {
        return avaliacaoSobreClienteRepository.save(avaliacao);
    }

    public void delete(int id) {
        avaliacaoSobreClienteRepository.delete(id);
    }

}