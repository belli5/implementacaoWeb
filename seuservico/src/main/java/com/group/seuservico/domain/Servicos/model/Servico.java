package com.group.seuservico.domain.Servicos.model;

public class Servico {

    private final int id;
    private final String nomeServico;
    private final String descricao;
    private final float valor;

    public Servico(int id, String nomeServico, String descricao, float valor) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.descricao = descricao;
        this.valor = valor;
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
    
}
