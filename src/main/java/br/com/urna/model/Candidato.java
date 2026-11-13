package br.com.urna.model;

public final class Candidato extends Pessoa {
    private final String numero;
    private final String partido;
    private final String cargoId;
    private final String fotoUrl;

    public Candidato(String nome, String numero, String partido, String cargoId, String fotoUrl) {
        super(nome);
        this.numero = numero;
        this.partido = partido;
        this.cargoId = cargoId;
        this.fotoUrl = fotoUrl;
    }

    public String getNumero() { return numero; }
    public String getPartido() { return partido; }
    public String getCargoId() { return cargoId; }
    public String getFotoUrl() { return fotoUrl; }
}
