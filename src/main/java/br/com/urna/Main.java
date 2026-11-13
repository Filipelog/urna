package br.com.urna;

import br.com.urna.config.FirebaseConfig;
import br.com.urna.controller.UrnaController;
import br.com.urna.repository.FirestoreRepository;
import br.com.urna.repository.LocalRepository;
import br.com.urna.repository.Repository;
import br.com.urna.service.VotacaoService;

import javax.swing.SwingUtilities;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        Repository repository = FirebaseConfig.createRepository()
                .orElseGet(LocalRepository::new);
        SwingUtilities.invokeLater(() ->
                new UrnaController(new VotacaoService(repository)).mostrarMenu());
    }
}
