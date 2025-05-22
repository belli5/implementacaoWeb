package com.exemple.implementacaoweb2.prestacaoServico;

import java.util.List;
import java.util.Optional;

public class PrestacaoServicoService {

    private final PrestacaoServicoRepository servicoRepository;

    public PrestacaoServicoService(PrestacaoServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public PrestacaoServico cadastrarServico(PrestacaoServico prestacaoServico) {
        return servicoRepository.save(prestacaoServico);
    }

    public void deletarServico(int id) {
        servicoRepository.delete(id);
    }

    public void atualizarServico(int id) {
        servicoRepository.update(id);
    }

    public Optional<PrestacaoServico> buscarPorId(int id) {
        return servicoRepository.findById(id);
    }

    public List<PrestacaoServico> buscarPorBairro(String bairro) {
        return servicoRepository.buscarPorBairro(bairro);
    }

    public List<PrestacaoServico> buscarPorCategoria(String categoria) {
        return servicoRepository.buscarPorCategoria(categoria);
    }

    public List<PrestacaoServico> buscarPorPrestador(int prestadorId) {
        return servicoRepository.buscarPorPrestadorId(prestadorId);
    }

    public List<PrestacaoServico> buscarTodos() {
        return servicoRepository.findAll();
    }
}
