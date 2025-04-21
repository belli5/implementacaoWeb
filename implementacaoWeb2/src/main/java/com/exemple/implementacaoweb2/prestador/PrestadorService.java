package com.exemple.implementacaoweb2.prestador;

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