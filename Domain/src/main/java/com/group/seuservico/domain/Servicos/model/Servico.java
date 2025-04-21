package com.group.seuservico.domain.Servicos.model;

public class Servico {

    private final int id;
    private final String nomeServico;
    private final String descricao;
    private final float valor;
    private final String bairro;

    public Servico(int id, String nomeServico, String descricao, float valor, String bairro) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.descricao = descricao;
        this.valor = valor;
        this.bairro = bairro;
    }

    public int getId() {
        return id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getValor() {
        return valor;
    }

    public String getBairro(){return bairro;}
    
}