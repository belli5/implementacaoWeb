package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.mappers.compartilhados.EnderecoMapper;

import static org.springframework.util.Assert.notNull;

public class PrestadorMapper {

    public static PrestadorJpa toPrestadorJpa(Prestador prestador) {
        notNull(prestador, "Prestador não pode ser nulo");

        PrestadorJpa jpa = new PrestadorJpa();
        jpa.setId(prestador.getId());
        jpa.setNome(prestador.getNome());
        jpa.setSenha(prestador.getSenha());
        jpa.setEmail(prestador.getEmail());
        jpa.setTelefone(prestador.getTelefone());
        return jpa;
    }

    public static Prestador toPrestador(PrestadorJpa jpa) {
        notNull(jpa, "PrestadorJpa não pode ser nulo");

        return new Prestador(
                jpa.getId(),
                jpa.getNome(),
                jpa.getSenha(),
                jpa.getEmail(),
                jpa.getTelefone(),
                EnderecoMapper.toEndereco(jpa.getEndereco())
        );
    }
}
