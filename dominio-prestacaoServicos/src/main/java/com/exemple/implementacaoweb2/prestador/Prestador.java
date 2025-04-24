package com.exemple.implementacaoweb2.prestador;


import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;

import java.util.List;

public class Prestador {

    private final int id;
    private final String nome;
    private List<PrestacaoServico> servicos;
    private final String email;
    private final String telefone;
    private final Endereco endereco;

    public Prestador(int id, String nome, List<PrestacaoServico> servicos, String email, String telefone, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.servicos = servicos;
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

    public List<PrestacaoServico> getServicos() {
        return servicos;
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

    public void adicionarServico(PrestacaoServico novoServico){
        this.servicos.add(novoServico);
    }

    @Override
    public String toString() {
        return "Prestador:" +
                "id:" + id +
                ", nome:"+ nome +
                ", especialidade:" + servicos +
                ", email:" + email +
                ", telefone:" + telefone +
                ", endereco:" + endereco;
    }
}
