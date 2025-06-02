package com.exemple.backend.infraestrutura.jpamodels;

import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cliente")
public class ClienteJpa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nome;

    @Column
    private String senha;

    @Column
    private String email;

    @Column
    private String telefone;

    @Embedded
    private EnderecoJpa endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AvaliacaoSobreClienteJpa> avaliacoesRecebidas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AvaliacaoSobrePrestadorJpa> avaliacoesFeitas;

    @OneToMany (mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FavoritadoJpa> favoritados;

    @OneToMany (mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PedidoJpa> pedidosFeitos;

    public ClienteJpa(){

    }

    public ClienteJpa(String nome, String senha, String email, String telefone, EnderecoJpa endereco) {
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

    public List<AvaliacaoSobreClienteJpa> getAvaliacoesRecebidas() {
        return avaliacoesRecebidas;
    }

    public void setAvaliacoesRecebidas(List<AvaliacaoSobreClienteJpa> avaliacoesRecebidas) {
        this.avaliacoesRecebidas = avaliacoesRecebidas;
    }

    public List<AvaliacaoSobrePrestadorJpa> getAvaliacoesFeitas() {
        return avaliacoesFeitas;
    }

    public void setAvaliacoesFeitas(List<AvaliacaoSobrePrestadorJpa> avaliacoesFeitas) {
        this.avaliacoesFeitas = avaliacoesFeitas;
    }

    public List<FavoritadoJpa> getFavoritados() {
        return favoritados;
    }

    public void setFavoritados(List<FavoritadoJpa> favoritados) {
        this.favoritados = favoritados;
    }

    public List<PedidoJpa> getPedidosFeitos() {
        return pedidosFeitos;
    }

    public void setPedidosFeitos(List<PedidoJpa> pedidosFeitos) {
        this.pedidosFeitos = pedidosFeitos;
    }

    @Override
    public String toString() {
        return String.format(
                "ClienteJpa{id=%d, nome='%s', email='%s', telefone='%s', endereco=%s}",
                id, nome, email, telefone, endereco
        );
    }
}
