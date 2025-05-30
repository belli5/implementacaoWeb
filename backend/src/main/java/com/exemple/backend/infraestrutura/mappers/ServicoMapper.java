package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;

import static org.springframework.util.Assert.notNull;

public class ServicoMapper {

    public static ServicoJpa toServicoJpa(Servico servico) {
        notNull(servico, "Servico não pode ser nulo");

        return new ServicoJpa(
                servico.getNome(),
                servico.getCategoria(),
                servico.getDescricao()
        );
    }

    public static Servico toServico(ServicoJpa servicoJpa) {
        notNull(servicoJpa, "ServicoJpa não pode ser nulo");

        return new Servico(
                servicoJpa.getNome(),
                servicoJpa.getCategoria(),
                servicoJpa.getDescricao()
        );
    }
}
