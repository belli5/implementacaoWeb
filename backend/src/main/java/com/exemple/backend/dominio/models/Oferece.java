package com.exemple.backend.dominio.models;

import static org.springframework.util.Assert.notNull;

public class Oferece {
    private int id;
    private Prestador prestador;
    private Servico servico;

    public Oferece() {
    }

    public Oferece(int id, Prestador prestador, Servico servico) {
        notNull(prestador, "Prestador não pode ser nulo");
        notNull(servico, "Serviço não pode ser nulo");

        this.prestador = prestador;
        this.servico = servico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return String.format("Oferece{prestador=%s, servico=%s}", prestador, servico);
    }

}
