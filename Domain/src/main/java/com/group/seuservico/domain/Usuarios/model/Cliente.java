package com.group.seuservico.domain.Usuarios.model;

import java.util.List;

public class Cliente {

    private final int id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final Endereco endereco;
    private final List<Prestador> prestadoresFavoritos;

    public Cliente(int id, String nome, String email, String telefone, Endereco endereco,  List<Prestador> prestadoresFavoritos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.prestadoresFavoritos = prestadoresFavoritos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public List<Prestador> getPrestadoresFavoritos() {
        return prestadoresFavoritos;
    }

}
