package com.exemple.backend.dominio.services;


import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobreClienteRepository;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobrePrestadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoSobrePrestadorService {
    private final AvaliacaoSobrePrestadorRepository avaliacaoSobrePrestadorRepository;

    public AvaliacaoSobrePrestadorService(AvaliacaoSobrePrestadorRepository avaliacaoSobrePrestadorRepository) {
        this.avaliacaoSobrePrestadorRepository = avaliacaoSobrePrestadorRepository;
    }

    public AvaliacaoSobrePrestador save(AvaliacaoSobrePrestador avaliacaoSobrePrestador){
        return avaliacaoSobrePrestadorRepository.save(avaliacaoSobrePrestador);
    }

    public void delete(int id){
        avaliacaoSobrePrestadorRepository.delete(id);
    }

    public List<AvaliacaoSobrePrestador> findByPrestadorId(int prestador_Id){
        return avaliacaoSobrePrestadorRepository.findByPrestadorId(prestador_Id);
    }

    public Optional<AvaliacaoSobrePrestador> findById(int id){
        return avaliacaoSobrePrestadorRepository.findById(id);
    }

    public List<AvaliacaoSobrePrestador> findAll(){
        return avaliacaoSobrePrestadorRepository.findAll();
    }
}
