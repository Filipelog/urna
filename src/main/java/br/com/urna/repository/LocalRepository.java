package br.com.urna.repository;

import br.com.urna.model.Candidato;
import br.com.urna.model.Voto;
import java.util.ArrayList;
import java.util.List;

public final class LocalRepository implements Repository {
    private final List<Voto> votos = new ArrayList<>();
    private final List<Candidato> cadastrados = new ArrayList<>();

    @Override public List<Candidato> buscarCandidatos(String cargoId) {
        List<Candidato> candidatos = new ArrayList<>();
        if ("deputado".equals(cargoId)) {
            candidatos.add(new Candidato("Ana Souza", "1234", "Partido da Esperança", cargoId, ""));
            candidatos.add(new Candidato("Bruno Lima", "4567", "Partido Cidadão", cargoId, ""));
        } else {
            candidatos.add(new Candidato("Carla Mendes", "12", "Movimento Unido", cargoId, ""));
            candidatos.add(new Candidato("Diego Alves", "45", "Avança Brasil", cargoId, ""));
        }
        candidatos.addAll(cadastrados.stream().filter(c -> c.getCargoId().equals(cargoId)).toList());
        return candidatos;
    }

    @Override public void cadastrarCandidato(Candidato candidato) { cadastrados.add(candidato); }

    @Override public void registrarSimulacao(List<Voto> novosVotos) {
        votos.addAll(novosVotos);
    }
}
