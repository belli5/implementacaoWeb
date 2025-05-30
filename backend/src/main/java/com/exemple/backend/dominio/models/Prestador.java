package com.exemple.backend.dominio.models;

import com.exemple.backend.dominio.models.compartilhados.Endereco;

import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

public class Prestador implements Serializable {
    private Integer id;
    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private Endereco endereco;

    public Prestador() {
    }

    public Prestador(Integer id, String nome, String senha, String email, String telefone, Endereco endereco) {
        notNull(id, "Id do prestador não pode ser nulo");
        notNull(nome, "Nome do prestador não pode ser nulo");
        notNull(senha, "Senha não pode ser nula");
        notNull(email, "Email do prestador não pode ser nulo");
        notNull(telefone, "Telefone do prestador não pode ser nulo");
        notNull(endereco, "Endereço do prestador não pode ser nulo");

        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format(
                "Prestador{id=%d, nome='%s', senha='%s', email='%s', telefone='%s', endereco=%s}",
                id, nome, senha, email, telefone, endereco
        );
    }

}
