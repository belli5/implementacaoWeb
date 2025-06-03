package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.PrestadorRepository;
import com.exemple.backend.dominio.strategies.prestador.PrestadorValidationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestadorService {
    private final PrestadorRepository prestadorRepository;
    private final PrestadorValidationStrategy validacao;

    public PrestadorService(PrestadorRepository prestadorRepository, PrestadorValidationStrategy validacao){
        this.prestadorRepository = prestadorRepository;
        this.validacao = validacao;
    }

    public Prestador save(Prestador prestador){
        validacao.validar(prestador);
        return prestadorRepository.save(prestador);
    }

    public Prestador update(Prestador prestador) {
        validacao.validar(prestador);
        return prestadorRepository.update(prestador);
    }

    public Optional<Prestador> findById(int id) {
        return prestadorRepository.findById(id);
    }

    public List<Prestador> findAll() {
        return prestadorRepository.findAll();
    }

    public void delete(int id) {
        prestadorRepository.delete(id);
    }
}
