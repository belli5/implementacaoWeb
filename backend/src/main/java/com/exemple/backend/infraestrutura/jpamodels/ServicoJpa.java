package com.exemple.backend.infraestrutura.jpamodels;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "servico")
public class ServicoJpa implements Serializable {

    @Id
    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OfereceJpa> servicosOferecidos;

    @OneToMany (mappedBy = "servico", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PedidoJpa> pedidosEnvolvidos;

    public ServicoJpa(){

    }

    public ServicoJpa(String nome, String categoria, String descricao) {
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

    public List<OfereceJpa> getServicosOferecidos() {
        return servicosOferecidos;
    }

    public void setServicosOferecidos(List<OfereceJpa> servicosOferecidos) {
        this.servicosOferecidos = servicosOferecidos;
    }

    public List<PedidoJpa> getPedidosEnvolvidos() {
        return pedidosEnvolvidos;
    }

    public void setPedidosEnvolvidos(List<PedidoJpa> pedidosEnvolvidos) {
        this.pedidosEnvolvidos = pedidosEnvolvidos;
    }

    @Override
    public String toString() {
        return String.format(
                "ServicoJpa{nome='%s', categoria='%s', descricao='%s'}",
                nome, categoria, descricao
        );
    }

}
