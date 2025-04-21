package com.group.seuservico.domain.Usuarios.model;

public class Endereco {

    private final String rua;
    private final String bairro;
    private final String cidade;
    private final String estado;

    public Endereco(String rua, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getRua(){
        return rua; 
    }

    public String getBairro(){
        return bairro; 
    }

    public String getCidade(){ 
        return cidade; 
    }

    public String getEstado(){ 
        return estado; 
    }

}
