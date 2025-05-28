package com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa;

import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpa;
import jakarta.persistence.*;

@Entity
@Table(name = "prestacao_servico")
public class PrestacaoServicoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;
    private float valor;
    private String bairro;
    private String categoria;

    @ManyToOne
    private PrestadorJpa prestador;

    public PrestacaoServicoJpa() {
    }

    public PrestacaoServicoJpa(String descricao, float valor, String bairro, String categoria, PrestadorJpa prestador) {
        this.descricao = descricao;
        this.valor = valor;
        this.bairro = bairro;
        this.categoria = categoria;
        this.prestador = prestador;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public PrestadorJpa getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorJpa prestador) {
        this.prestador = prestador;
    }
}