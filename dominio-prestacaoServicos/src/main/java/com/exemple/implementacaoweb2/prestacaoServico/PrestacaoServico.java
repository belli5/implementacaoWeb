package com.exemple.implementacaoweb2.prestacaoServico;


public class PrestacaoServico {

    private final int id;
    private String descricao;
    private final float valor;
    private final String bairro;
    private final String categoria;
    private String prestador;

    public PrestacaoServico(int id, String descricao, float valor, String bairro, String categoria, String prestador) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.bairro = bairro;
        this.categoria = categoria;
        this.prestador = prestador;
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
