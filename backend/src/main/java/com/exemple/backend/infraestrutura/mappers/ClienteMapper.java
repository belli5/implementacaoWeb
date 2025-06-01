package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.mappers.compartilhados.EnderecoMapper;

import static org.springframework.util.Assert.notNull;

public class ClienteMapper {

    public static ClienteJpa toClienteJpa(Cliente cliente) {
        notNull(cliente, "Cliente não pode ser nulo");

        ClienteJpa jpa = new ClienteJpa();
        if (cliente.getId() != null) {
            jpa.setId(cliente.getId());
        }

        jpa.setNome(cliente.getNome());
        jpa.setSenha(cliente.getSenha());
        jpa.setEmail(cliente.getEmail());
        jpa.setTelefone(cliente.getTelefone());

        // Somente setamos EnderecoJpa se o JSON vier com endereco != null
        if (cliente.getEndereco() != null) {
            jpa.setEndereco(EnderecoMapper.toEnderecoJpa(cliente.getEndereco()));
        }
        return jpa;
    }

    public static Cliente toCliente(ClienteJpa jpa) {
        notNull(jpa, "ClienteJpa não pode ser nulo");
        return new Cliente(
                jpa.getId(),
                jpa.getNome(),
                jpa.getSenha(),
                jpa.getEmail(),
                jpa.getTelefone(),
                EnderecoMapper.toEndereco(jpa.getEndereco())
        );
    }
}
