package com.exemple.backend.dominio.models;

import com.exemple.backend.dominio.models.compartilhados.Endereco;

import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

public class Cliente implements Serializable {
    private Integer id;
    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String senha, String email, String telefone, Endereco endereco) {
        notNull(id, "Id do usuário não pode ser nulo");
        notNull(nome, "Nome do usuário não pode ser nulo");
        notNull(senha, "Senha do usuário não pode ser nula");
        notNull(email, "Email do usuário não pode ser nulo");
        notNull(telefone, "Telefone do usuário não pode ser nulo");
        notNull(endereco, "Endereço do usuário não pode ser nulo");

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

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format(
                "Cliente{id=%d, nome='%s', senha='%s', email='%s', telefone='%s', endereco=%s}",
                id, nome, senha, email, telefone, endereco
        );
    }
}
