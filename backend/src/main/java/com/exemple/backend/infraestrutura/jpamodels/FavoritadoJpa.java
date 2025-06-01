package com.exemple.backend.infraestrutura.jpamodels;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Favoritado")
public class FavoritadoJpa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "prestador_Id", referencedColumnName = "id")
    private PrestadorJpa prestador;

    @ManyToOne
    @JoinColumn(name = "cliente_Id", referencedColumnName = "id")
    private ClienteJpa cliente;

    public FavoritadoJpa(){

    }

    public FavoritadoJpa(int id, PrestadorJpa prestador, ClienteJpa cliente) {
        this.id = id;
        this.prestador = prestador;
        this.cliente = cliente;
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

    public ClienteJpa getCliente() {
        return cliente;
    }

    public void setCliente(ClienteJpa cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return String.format(
                "FavoritadoJpa{id=%d, prestadorId=%d, clienteId=%d}",
                id,
                prestador != null ? prestador.getId() : null,
                cliente != null ? cliente.getId() : null
        );
    }
}
