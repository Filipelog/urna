package br.com.urna.repository;

import br.com.urna.model.Candidato;
import br.com.urna.model.Voto;
import com.google.cloud.firestore.Firestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class FirestoreRepository implements Repository {
    private final Firestore firestore;

    public FirestoreRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<Candidato> buscarCandidatos(String cargoId) {
        try {
            List<Candidato> resultado = new ArrayList<>();
            for (var document : firestore.collection("candidatos").whereEqualTo("cargoId", cargoId)
                    .get().get().getDocuments()) {
                var data = document.getData();
                resultado.add(new Candidato(
                        String.valueOf(data.getOrDefault("nome", "")),
                        String.valueOf(data.getOrDefault("numero", "")),
                        String.valueOf(data.getOrDefault("partido", "")),
                        cargoId, String.valueOf(data.getOrDefault("fotoUrl", ""))));
            }
            return resultado;
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível consultar candidatos no Firestore.", e);
        }
    }

    @Override
    public void cadastrarCandidato(Candidato candidato) {
        try {
            firestore.collection("candidatos").add(Map.of("nome", candidato.getNome(), "numero", candidato.getNumero(),
                    "partido", candidato.getPartido(), "cargoId", candidato.getCargoId(), "fotoUrl", candidato.getFotoUrl())).get();
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível cadastrar o candidato no Firestore.", e);
        }
    }

    @Override
    public void registrarSimulacao(List<Voto> votos) {
        try {
            Map<String, Object> simulacao = new HashMap<>();
            simulacao.put("id", UUID.randomUUID().toString());
            simulacao.put("anonima", true);
            simulacao.put("criadaEm", com.google.cloud.firestore.FieldValue.serverTimestamp());
            simulacao.put("votos", votos.stream().map(v -> Map.of(
                    "cargoId", v.cargoId(), "numero", v.numero(), "branco", v.branco())).toList());
            firestore.collection("simulacoes").add(simulacao).get();
            for (Voto voto : votos) {
                firestore.collection("resultados").document(voto.cargoId() + "-" + (voto.branco() ? "BRANCO" : voto.numero()))
                        .set(Map.of("cargoId", voto.cargoId(), "numero", voto.branco() ? "BRANCO" : voto.numero(),
                                "total", com.google.cloud.firestore.FieldValue.increment(1L))).get();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível registrar a simulação no Firestore.", e);
        }
    }
}
