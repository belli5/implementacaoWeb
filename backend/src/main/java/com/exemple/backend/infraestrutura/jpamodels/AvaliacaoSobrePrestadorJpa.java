package com.exemple.backend.infraestrutura.jpamodels;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "AvaliacaoSobrePrestador")
public class AvaliacaoSobrePrestadorJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Id_Cliente", referencedColumnName = "id")
    private ClienteJpa cliente;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private int nota;

    @ManyToOne
    @JoinColumn(name = "Id_Prestador", referencedColumnName = "id")
    private PrestadorJpa prestador;

    public AvaliacaoSobrePrestadorJpa(){

    }

    public AvaliacaoSobrePrestadorJpa(int id, ClienteJpa cliente, String comentario, int nota, PrestadorJpa prestador) {
        this.id = id;
        this.cliente = cliente;
        this.comentario = comentario;
        this.nota = nota;
        this.prestador = prestador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClienteJpa getCliente() {
        return cliente;
    }

    public void setCliente(ClienteJpa cliente) {
        this.cliente = cliente;
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

    public PrestadorJpa getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorJpa prestador) {
        this.prestador = prestador;
    }

    @Override
    public String toString() {
        return String.format(
                "AvaliacaoSobrePrestadorJpa{id=%d, nota=%d, comentario='%s', clienteId=%d, prestadorId=%d}",
                id,
                nota,
                comentario,
                cliente != null ? cliente.getId() : null,
                prestador != null ? prestador.getId() : null
        );
    }

}
