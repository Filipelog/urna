package br.com.urna.model;

public final class CargoProporcional extends Cargo {
    public CargoProporcional() { super("deputado", "Deputado Federal", 4); }
    @Override public String getTipo() { return "PROPORCIONAL"; }
}
