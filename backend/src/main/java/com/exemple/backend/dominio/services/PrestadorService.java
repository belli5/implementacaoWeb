package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.PrestadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestadorService {
    private final PrestadorRepository prestadorRepository;

    public PrestadorService(PrestadorRepository prestadorRepository){
        this.prestadorRepository = prestadorRepository;
    }

    public Prestador save(Prestador prestador){
        return prestadorRepository.save(prestador);
    }

    public Prestador update(Prestador prestador) {
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
