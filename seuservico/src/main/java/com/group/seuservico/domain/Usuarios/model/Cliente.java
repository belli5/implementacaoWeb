package com.group.seuservico.domain.Usuarios.model;

public class Cliente {

    private final int id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final Endereco endereco;

    public Cliente(int id, String nome, String email, String telefone, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
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

}
