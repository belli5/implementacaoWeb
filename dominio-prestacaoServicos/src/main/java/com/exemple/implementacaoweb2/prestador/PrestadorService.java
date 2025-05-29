package com.exemple.implementacaoweb2.prestador;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;

import java.util.List;
import java.util.Optional;

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

    public void atualizarPrestador(Prestador prestador) throws Exception {
        for (PrestacaoServico servico : prestador.getServicos()) {
            if (servico.getDescricao() == null || servico.getDescricao().isEmpty()) {
                throw new Exception("Descrição do serviço é obrigatória.");
            }
        }
        prestadorRepository.save(prestador);
    }

    public Optional<Prestador> buscarPorId(int id) {
        return prestadorRepository.findById(id);
    }

    public List<Prestador> buscarPorServico(String categoria) {
        return prestadorRepository.findByServico(categoria);
    }

    public List<Prestador> listarTodos() {
        return prestadorRepository.findAll();
    }
}
