package com.exemple.implementacaoweb2.cliente;


import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestador.Prestador;

import java.util.List;

public class Cliente {

    private final int id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final Endereco endereco;
    private final List<Prestador> prestadoresFavoritos;
    public List<PrestacaoServico> historicoDeServicos;

    public Cliente(int id, String nome, String email, String telefone, Endereco endereco,  List<Prestador> prestadoresFavoritos, List<PrestacaoServico> historicoDeServicos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.prestadoresFavoritos = prestadoresFavoritos;
        this.historicoDeServicos = historicoDeServicos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
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

    public List<Prestador> getPrestadoresFavoritos() {
        return prestadoresFavoritos;
    }

    public List<PrestacaoServico> getHistoricoDeServicos() {return historicoDeServicos;}

}
