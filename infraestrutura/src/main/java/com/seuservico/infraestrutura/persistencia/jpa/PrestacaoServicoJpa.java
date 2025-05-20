package com.seuservico.infraestrutura.persistencia.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PrestacaoServicoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;
    private float valor;
    private String bairro;
    private String categoria;
    private String prestador;

    public PrestacaoServicoJpa(int id, String descricao, float valor, String bairro, String categoria, String prestador) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.bairro = bairro;
        this.categoria = categoria;
        this.prestador = prestador;
    }

    public PrestacaoServicoJpa() {

    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getValor() {
        return valor;
    }

    public String getBairro(){return bairro;}

    public String getCategoria() {
        return categoria;
    }

    public void setDescricao(String descricao){this.descricao = descricao;}

    public void setPrestador(String prestador) {
        this.prestador = prestador;
    }

    public String getPrestador() {
        return prestador;
    }
}
