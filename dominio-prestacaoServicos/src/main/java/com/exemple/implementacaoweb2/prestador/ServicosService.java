package com.exemple.implementacaoweb2.prestador;

import java.util.ArrayList;
import java.util.List;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;

public class ServicosService {

    private List<PrestacaoServico> servicos = new ArrayList<>();

    public String adicionarServico(PrestacaoServico servico) {
        if (servico.getNomeServico() == null || servico.getNomeServico().trim().isEmpty()) {
            return "Nome do serviço é obrigatório.";
        }
        if (!servicos.contains(servico)) {
            servicos.add(servico);
            return "Serviço adicionado com sucesso!";
        } else {
            return "Não é possível adicionar o servico duas vezes.";
        }
    }

    public String removerServico(PrestacaoServico servico) {
        if (servicos.contains(servico)) {
            servicos.remove(servico);
            return "Serviço removido com sucesso!";
        } else {
            return "Não é possível remover o serviço duas vezes.";
        }
    }
}