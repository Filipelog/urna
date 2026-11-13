package br.com.urna.repository;

import br.com.urna.model.Candidato;
import br.com.urna.model.Voto;
import java.util.List;

public interface Repository {
    List<Candidato> buscarCandidatos(String cargoId);
    void cadastrarCandidato(Candidato candidato);
    void registrarSimulacao(List<Voto> votos);
}
