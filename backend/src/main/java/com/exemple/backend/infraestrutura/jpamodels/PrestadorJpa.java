package com.exemple.backend.infraestrutura.jpamodels;

import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "Prestador")
public class PrestadorJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Embedded
    private EnderecoJpa endereco;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OfereceJpa> servicosOferecidos;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AvaliacaoSobreClienteJpa> avaliacoesFeitas;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AvaliacaoSobrePrestadorJpa> avaliacoesRecebidas;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FavoritadoJpa> favoritadoPor;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PedidoJpa> pedidosRecebidos;


    public PrestadorJpa(){}

    public PrestadorJpa(String nome, String senha, String email, String telefone, EnderecoJpa endereco){
        this.nome = nome;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public EnderecoJpa getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoJpa endereco) {
        this.endereco = endereco;
    }

    public List<OfereceJpa> getServicosOferecidos() {
        return servicosOferecidos;
    }

    public void setServicosOferecidos(List<OfereceJpa> servicosOferecidos) {
        this.servicosOferecidos = servicosOferecidos;
    }

    public List<AvaliacaoSobreClienteJpa> getAvaliacoesFeitas() {
        return avaliacoesFeitas;
    }

    public void setAvaliacoesFeitas(List<AvaliacaoSobreClienteJpa> avaliacoesFeitas) {
        this.avaliacoesFeitas = avaliacoesFeitas;
    }

    public List<AvaliacaoSobrePrestadorJpa> getAvaliacoesRecebidas() {
        return avaliacoesRecebidas;
    }

    public void setAvaliacoesRecebidas(List<AvaliacaoSobrePrestadorJpa> avaliacoesRecebidas) {
        this.avaliacoesRecebidas = avaliacoesRecebidas;
    }

    public List<FavoritadoJpa> getFavoritadoPor() {
        return favoritadoPor;
    }

    public void setFavoritadoPor(List<FavoritadoJpa> favoritadoPor) {
        this.favoritadoPor = favoritadoPor;
    }

    public List<PedidoJpa> getPedidosRecebidos() {
        return pedidosRecebidos;
    }

    public void setPedidosRecebidos(List<PedidoJpa> pedidosRecebidos) {
        this.pedidosRecebidos = pedidosRecebidos;
    }

    @Override
    public String toString() {
        return String.format(
                "PrestadorJpa{id=%d, nome='%s', senha='%s', email='%s', telefone='%s', endereco=%s}",
                id, nome, senha, email, telefone, endereco
        );
    }

}
