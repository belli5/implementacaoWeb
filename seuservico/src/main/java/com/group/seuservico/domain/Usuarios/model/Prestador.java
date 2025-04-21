package com.group.seuservico.domain.Usuarios.model;

import javax.persistence.*;

public class Prestador {

    private final int id;
    private final String nome;
    private final String tipoServico;
    private final String email;
    private final String telefone;
    private final Endereco endereco;

    public Prestador(int id, String nome, String tipoServico, String email, String telefone, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.tipoServico = tipoServico;
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

    public String getTipoServico() {
        return tipoServico;
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

    @Override
    public String toString() {
        return "Prestador:" +
                "id:" + id +
                ", nome:"+ nome +
                ", especialidade:" + tipoServico +
                ", email:" + email +
                ", telefone:" + telefone +
                ", endereco:" + endereco;
    }
}
