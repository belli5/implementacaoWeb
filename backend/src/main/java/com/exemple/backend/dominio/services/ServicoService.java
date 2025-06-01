package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.repositorys.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico save(Servico servico) {
        return servicoRepository.save(servico);
    }

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    public List<Servico> findByCategoria(String categoria) {
        return servicoRepository.findByCategoria(categoria);
    }

    public Optional<Servico> findByNome(String nome) {
        return servicoRepository.findByNome(nome);
    }

    @Transactional
    public boolean deleteByNome(String nome) {
        Optional<Servico> servico = servicoRepository.findByNome(nome);
        if (servico.isPresent()) {
            servicoRepository.deleteByNome(servico.get().getNome());
            return true;
        }
        return false;
    }
}
