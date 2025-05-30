package com.exemple.backend.dominio.models;

import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

public class AvaliacaoSobreCliente implements Serializable {
    private int id;
    private Prestador prestador;
    private String comentario;
    private int nota;
    private Cliente cliente;

    public AvaliacaoSobreCliente() {}

    public AvaliacaoSobreCliente(int id, Prestador prestador, String comentario, int nota, Cliente cliente) {
        notNull(prestador, "Prestador não pode ser nulo");
        notNull(comentario, "Comentário não pode ser nulo");
        notNull(cliente, "Cliente não pode ser nulo");

        this.id = id;
        this.prestador = prestador;
        this.comentario = comentario;
        this.nota = nota;
        this.cliente = cliente;
    }

    // Getters e setters


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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return String.format(
                "AvaliacaoSobreCliente{id=%d, nota=%d, comentario='%s', cliente=%s, prestador=%s}",
                id, nota, comentario, cliente.getId(), prestador.getId()
        );
    }
}
