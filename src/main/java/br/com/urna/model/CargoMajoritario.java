package br.com.urna.model;

public final class CargoMajoritario extends Cargo {
    public CargoMajoritario() { super("governador", "Governador", 2); }
    @Override public String getTipo() { return "MAJORITÁRIO"; }
}
