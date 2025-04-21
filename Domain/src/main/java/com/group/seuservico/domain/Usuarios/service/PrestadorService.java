package com.group.seuservico.domain.Usuarios.service;

import com.group.seuservico.domain.Usuarios.model.Prestador;
import com.group.seuservico.domain.Usuarios.repository.PrestadorRepository;

public class PrestadorService {

    private final PrestadorRepository prestadorRepository;

    public PrestadorService(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    public Prestador cadastrarPrestador(Prestador prestador) {
        return prestadorRepository.save(prestador);
    }

    public void deletarPrestador(int id) {
        prestadorRepository.delete(id);
    }

    public void atualizarPrestador(int id) {
        prestadorRepository.update(id);
    }
}