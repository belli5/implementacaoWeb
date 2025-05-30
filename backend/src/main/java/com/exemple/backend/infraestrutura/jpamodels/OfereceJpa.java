package com.exemple.backend.infraestrutura.jpamodels;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Oferece")
public class OfereceJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Id_Prestador", referencedColumnName = "id")
    private PrestadorJpa prestador;

    @ManyToOne
    @JoinColumn(name = "nome_Servico", referencedColumnName = "nome")
    private ServicoJpa servico;

    public OfereceJpa(){

    }

    public OfereceJpa(int id, PrestadorJpa prestador, ServicoJpa servico) {
        this.id = id;
        this.prestador = prestador;
        this.servico = servico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PrestadorJpa getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorJpa prestador) {
        this.prestador = prestador;
    }

    public ServicoJpa getServico() {
        return servico;
    }

    public void setServico(ServicoJpa servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return String.format("OfereceJpa{id=%d, prestador=%s, servico=%s}", id, prestador.getId(), servico.getNome());
    }
}
