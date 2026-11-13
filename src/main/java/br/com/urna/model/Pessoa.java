package br.com.urna.model;

public abstract class Pessoa {
    private final String nome;

    protected Pessoa(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório");
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
