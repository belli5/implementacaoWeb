package com.exemple.backend.dominio.models;

import java.io.Serializable;
import static org.springframework.util.Assert.notNull;

public class Favoritado implements Serializable {
    private int id;
    private Cliente cliente;
    private Prestador prestador;

    public Favoritado() {
    }

    public Favoritado(int id, Cliente cliente, Prestador prestador) {
        notNull(cliente, "Cliente não pode ser nulo");
        notNull(prestador, "Prestador não pode ser nulo");

        this.id = id;
        this.cliente = cliente;
        this.prestador = prestador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    @Override
    public String toString() {
        return String.format("Favoritado{cliente=%s, prestador=%s}", cliente, prestador);
    }


}
