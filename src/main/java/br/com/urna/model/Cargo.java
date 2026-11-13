package br.com.urna.model;

public abstract class Cargo {
    private final String id;
    private final String nome;
    private final int quantidadeDigitos;

    protected Cargo(String id, String nome, int quantidadeDigitos) {
        this.id = id;
        this.nome = nome;
        this.quantidadeDigitos = quantidadeDigitos;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public int getQuantidadeDigitos() { return quantidadeDigitos; }
    public abstract String getTipo();
}
