package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.PrestadorRepository;
import com.exemple.backend.dominio.services.template.CadastroPrestador;
import com.exemple.backend.dominio.strategies.prestador.PrestadorValidationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;
    private final PrestadorValidationStrategy validacao;
    private final CadastroPrestador cadastroPrestador;

    public PrestadorService(PrestadorRepository prestadorRepository,
                            PrestadorValidationStrategy validacao,
                            CadastroPrestador cadastroPrestador) {
        this.prestadorRepository = prestadorRepository;
        this.validacao = validacao;
        this.cadastroPrestador = cadastroPrestador;
    }

    public Prestador save(Prestador prestador) {
        return cadastroPrestador.cadastrar(prestador); // uso do Template Method
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
