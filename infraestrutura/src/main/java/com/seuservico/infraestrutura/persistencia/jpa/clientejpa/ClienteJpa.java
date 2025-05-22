package com.seuservico.infraestrutura.persistencia.jpa.clientejpa;

import com.seuservico.infraestrutura.persistencia.jpa.enderecojpa.EnderecoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa.PrestacaoServicoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpa;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class ClienteJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String email;
    private String telefone;

    @Embedded
    private EnderecoJpa endereco;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrestadorJpa> prestadoresFavoritos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrestacaoServicoJpa> historicoDeServicos = new ArrayList<>();

    public ClienteJpa() {}

    public ClienteJpa(String nome, String email, String telefone, EnderecoJpa endereco,
                       List<PrestadorJpa> prestadoresFavoritos, List<PrestacaoServicoJpa> historicoDeServicos) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.prestadoresFavoritos = prestadoresFavoritos != null ? prestadoresFavoritos : new ArrayList<>();
        this.historicoDeServicos = historicoDeServicos != null ? historicoDeServicos : new ArrayList<>();
    }

    // Getters e setters
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
    public List<PrestadorJpa> getPrestadoresFavoritos() {
        return prestadoresFavoritos;
    }
    public void setPrestadoresFavoritos(List<PrestadorJpa> prestadoresFavoritos) {
        this.prestadoresFavoritos = prestadoresFavoritos;
    }
    public List<PrestacaoServicoJpa> getHistoricoDeServicos() {
        return historicoDeServicos;
    }
    public void setHistoricoDeServicos(List<PrestacaoServicoJpa> historicoDeServicos) {
        this.historicoDeServicos = historicoDeServicos;
    }
}
