package com.seuservico.infraestrutura.persistencia.jpa;

import com.exemple.implementacaoweb2.avaliacao.TipoAvaliacao;
import jakarta.persistence.*;

@Entity
public class AvaliacaoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int prestadorId;
    private int clienteId;
    private float nota;

    @Column(length = 1000)
    private String comentario;

    @Enumerated(EnumType.STRING)
    private TipoAvaliacao tipoAvaliacao;

    public AvaliacaoJpa() {
    }

    public AvaliacaoJpa(int prestadorId, int clienteId, float nota, String comentario, TipoAvaliacao tipoAvaliacao) {
        this.prestadorId = prestadorId;
        this.clienteId = clienteId;
        this.nota = nota;
        this.comentario = comentario;
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPrestadorId() {
        return prestadorId;
    }
    public void setPrestadorId(int prestadorId) {
        this.prestadorId = prestadorId;
    }

    public int getClienteId() {
        return clienteId;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public float getNota() {
        return nota;
    }
    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }
    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }
}