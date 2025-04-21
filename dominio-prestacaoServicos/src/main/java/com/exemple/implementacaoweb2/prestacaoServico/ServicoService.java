package com.exemple.implementacaoweb2.prestacaoServico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicoService {
    private List<Servico> servicos = new ArrayList<>();

    public void adicionarServico(Servico servico) {
        servicos.add(servico);
    }

    public List<Servico> filtrarPorCategoria(String categoria) {
        return servicos.stream()
                .filter(s -> s.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
}
