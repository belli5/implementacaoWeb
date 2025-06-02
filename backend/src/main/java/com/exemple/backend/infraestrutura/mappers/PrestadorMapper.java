package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;

import static org.springframework.util.Assert.notNull;

public class PrestadorMapper {

    public static PrestadorJpa toPrestadorJpa(Prestador prestador) {
        notNull(prestador, "Prestador não pode ser nulo");

        EnderecoJpa enderecoJpa = null;
        if (prestador.getEndereco() != null) {
            Endereco e = prestador.getEndereco();
            enderecoJpa = new EnderecoJpa(
                    e.getRua(),
                    e.getBairro(),
                    e.getCidade(),
                    e.getEstado()
            );
        }

        return new PrestadorJpa(
                prestador.getId(),
                prestador.getNome(),
                prestador.getSenha(),
                prestador.getEmail(),
                prestador.getTelefone(),
                enderecoJpa
        );
    }

    public static Prestador toPrestador(PrestadorJpa jpa) {
        notNull(jpa, "PrestadorJpa não pode ser nulo");

        Endereco endereco = null;
        if (jpa.getEndereco() != null) {
            EnderecoJpa e = jpa.getEndereco();
            endereco = new Endereco(
                    e.getRua(),
                    e.getBairro(),
                    e.getCidade(),
                    e.getEstado()
            );
        }

        return new Prestador(
                jpa.getId(),
                jpa.getNome(),
                jpa.getSenha(),
                jpa.getEmail(),
                jpa.getTelefone(),
                endereco
        );
    }
}
