package com.mydogs.mydogs;

/**
 * Created by brunoferreira on 04/03/18.
 */

public class Animal {
    private String Id;
    private String nome;
    private String raca;

    public Animal(){}

    public Animal(String id, String nome, String raca) {
        Id = id;
        this.nome = nome;
        this.raca = raca;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
