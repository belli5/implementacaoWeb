package com.exemple.backend.dominio.models;

import java.io.Serializable;
import static org.springframework.util.Assert.notNull;

public class Servico implements Serializable {
    private String nome;
    private String categoria;
    private String descricao;

    public Servico() {
    }

    public Servico(String nome, String categoria, String descricao) {
        notNull(nome, "Nome do serviço não pode ser nulo");
        notNull(categoria, "Categoria do serviço não pode ser nula");
        notNull(descricao, "Descrição do serviço não pode ser nula");

        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return String.format(
                "Servico{nome='%s', categoria='%s', descricao='%s'}",
                nome, categoria, descricao
        );
    }

}
