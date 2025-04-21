package com.exemple.implementacaoweb2.prestacaoServico;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PrestacaoServico> buscarServicoPorBairro(String bairro){
        return servicoRepository.buscarPorBairro(bairro);
    }

    public List<PrestacaoServico> filtrarPorCategoria(String categoria) {
        List<PrestacaoServico> todosServicos = servicoRepository.findAll();

        return todosServicos.stream()
                .filter(s -> s.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
}