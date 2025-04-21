package com.exemple.implementacaoweb2.prestacaoServico;


public class Servico {
    private int id;
    private String descricao;
    private String categoria;

    public Servico(int id, String descricao, String categoria) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}
