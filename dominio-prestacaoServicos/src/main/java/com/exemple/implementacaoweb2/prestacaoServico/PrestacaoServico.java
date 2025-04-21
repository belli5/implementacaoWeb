package com.exemple.implementacaoweb2.prestacaoServico;


public class PrestacaoServico {

    private final int id;
    private final String descricao;
    private final float valor;
    private final String bairro;
    private final String categoria;

    public PrestacaoServico(int id, String descricao, float valor, String bairro, String categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.bairro = bairro;
        this.categoria = categoria;
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

}
