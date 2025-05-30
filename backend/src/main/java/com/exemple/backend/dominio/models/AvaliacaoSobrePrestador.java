package com.exemple.backend.dominio.models;

import java.io.Serializable;
import static org.springframework.util.Assert.notNull;

public class AvaliacaoSobrePrestador implements Serializable {
    private Integer id;
    private Cliente cliente;
    private String comentario;
    private Integer nota;
    private Prestador prestador;

    public AvaliacaoSobrePrestador() {}

    public AvaliacaoSobrePrestador(Integer id, Cliente cliente, String comentario, Integer nota, Prestador prestador) {
        notNull(id, "Id da avaliação não pode ser nulo");
        notNull(cliente, "Cliente não pode ser nulo");
        notNull(comentario, "Comentário não pode ser nulo");
        notNull(nota, "Nota não pode ser nula");
        notNull(prestador, "Prestador não pode ser nulo");

        this.id = id;
        this.cliente = cliente;
        this.comentario = comentario;
        this.nota = nota;
        this.prestador = prestador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
