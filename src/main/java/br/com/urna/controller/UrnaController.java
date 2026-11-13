package br.com.urna.controller;

import br.com.urna.model.*;
import br.com.urna.service.VotacaoService;
import br.com.urna.view.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;

public final class UrnaController {
    private final VotacaoService service;
    private final List<Voto> votos = new ArrayList<>();

    public UrnaController(VotacaoService service) { this.service = service; }
    public void mostrarMenu() { new MenuFrame(this::iniciar, this::cadastrar).setVisible(true); }
    private void cadastrar() {
        JTextField nome = new JTextField();
        JTextField numero = new JTextField();
        JTextField partido = new JTextField();
        JComboBox<String> cargo = new JComboBox<>(new String[]{"deputado", "governador"});
        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));
        painel.add(new JLabel("Nome:")); painel.add(nome);
        painel.add(new JLabel("Número:")); painel.add(numero);
        painel.add(new JLabel("Partido:")); painel.add(partido);
        painel.add(new JLabel("Cargo:")); painel.add(cargo);
        if (JOptionPane.showConfirmDialog(null, painel, "Cadastrar candidato", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (nome.getText().isBlank() || numero.getText().isBlank() || partido.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos."); return;
            }
            try {
                service.cadastrar(new Candidato(nome.getText().trim(), numero.getText().trim(), partido.getText().trim(),
                        (String) cargo.getSelectedItem(), ""));
                JOptionPane.showMessageDialog(null, "Candidato cadastrado com sucesso.");
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de persistência", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void iniciar() {
        votos.clear();
        abrirCargo(new CargoProporcional(), () -> abrirCargo(new CargoMajoritario(), this::encerrar));
    }
    private void abrirCargo(Cargo cargo, Runnable proximo) {
        final VotacaoFrame[] frameRef = new VotacaoFrame[1];
        frameRef[0] = new VotacaoFrame(cargo, service.candidatos(cargo), voto -> {
            votos.add(voto); frameRef[0].dispose(); proximo.run();
        });
        frameRef[0].setVisible(true);
    }
    private void encerrar() {
        try {
            service.finalizar(votos);
            new FimFrame(this::mostrarMenu).setVisible(true);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de persistência", JOptionPane.ERROR_MESSAGE);
        }
    }
}
