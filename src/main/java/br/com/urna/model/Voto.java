package br.com.urna.model;

import java.time.Instant;

public record Voto(String cargoId, String numero, boolean branco, Instant criadoEm) {
    public static Voto branco(String cargoId) {
        return new Voto(cargoId, "", true, Instant.now());
    }
    public static Voto nominal(String cargoId, String numero) {
        return new Voto(cargoId, numero, false, Instant.now());
    }
}
