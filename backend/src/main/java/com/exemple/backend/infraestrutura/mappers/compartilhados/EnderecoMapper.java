package com.exemple.backend.infraestrutura.mappers.compartilhados;

import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;

import static org.springframework.util.Assert.notNull;
public class EnderecoMapper {

    public static EnderecoJpa toEnderecoJpa(Endereco endereco){
        notNull(endereco, "Endereco não pode ser nulo");
        return new EnderecoJpa(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }

    public static Endereco toEndereco(EnderecoJpa enderecoJpa){
        notNull(enderecoJpa, "EnderecoJpa não pode ser nulo");
        return new Endereco(enderecoJpa.getRua(), enderecoJpa.getBairro(), enderecoJpa.getCidade(), enderecoJpa.getEstado());
    }
}
