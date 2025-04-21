package com.group.seuservico.domain.Avaliacao.model;

public class Avaliacao {

    private final int id;
    private final int prestadorId;
    private final int clienteId;
    private final float nota;
    private final String comentario;

    public Avaliacao(int id, int prestadorId, int clienteId, float nota, String comentario) {
        this.id = id;
        this.prestadorId = prestadorId;
        this.clienteId = clienteId;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public int getPrestadorId() {
        return prestadorId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public float getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }
    
}
