package com.seuservico.infraestrutura.persistencia.jpa;


import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class PrestadorJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrestacaoServicoJpa> servicos;

    private String email;
    private String telefone;

    @Embedded
    private Endereco endereco;

    public PrestadorJpa() {

    }

    public PrestadorJpa(String nome, List<PrestacaoServicoJpa> servicos, String email, String telefone, Endereco endereco) {
        this.nome = nome;
        this.servicos = servicos;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<PrestacaoServicoJpa> getServicos() {
        return servicos;
    }
    public void setServicos(List<PrestacaoServicoJpa> servicos) {
        this.servicos = servicos;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void adicionarServico(PrestacaoServicoJpa novoServico){
        this.servicos.add(novoServico);
    }

    @Override
    public String toString() {
        return "Prestador:" +
                "id:" + id +
                ", nome:" + nome +
                ", especialidade:" + servicos +
                ", email:" + email +
                ", telefone:" + telefone +
                ", endereco:" + endereco;
    }
}