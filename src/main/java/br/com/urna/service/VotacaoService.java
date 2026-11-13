package br.com.urna.service;

import br.com.urna.model.*;
import br.com.urna.repository.Repository;
import java.util.ArrayList;
import java.util.List;

public final class VotacaoService {
    private final Repository repository;

    public VotacaoService(Repository repository) { this.repository = repository; }
    public List<Candidato> candidatos(Cargo cargo) {
        List<Candidato> encontrados = repository.buscarCandidatos(cargo.getId());
        return encontrados.isEmpty() ? new br.com.urna.repository.LocalRepository().buscarCandidatos(cargo.getId()) : encontrados;
    }
    public void cadastrar(Candidato candidato) { repository.cadastrarCandidato(candidato); }
    public void finalizar(List<Voto> votos) { repository.registrarSimulacao(new ArrayList<>(votos)); }
}
