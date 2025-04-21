package org.example.service.servicos;

import com.group.seuservico.domain.Servicos.model.Servico;
import com.group.seuservico.domain.Servicos.repository.ServicoRepository;

import java.util.List;

public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico cadastrarServico(Servico servico) {
        return servicoRepository.save(servico);
    }

    public void deletarServico(int id) {
        servicoRepository.delete(id);
    }

    public void atualizarServico(int id) {
        servicoRepository.update(id);
    }

    public List<Servico> buscarServicoPorBairro(String bairro){
        return servicoRepository.buscarPorBairro(bairro);
    }
}
