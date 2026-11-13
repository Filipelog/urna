package br.com.urna.view;

import javax.swing.*;
import java.awt.*;

public final class MenuFrame extends TelaBase {
    public MenuFrame(Runnable iniciar, Runnable cadastrar) {
        super("Urna Eletrônica - Menu inicial");
        setLayout(new BorderLayout());
        add(painelTitulo("URNA ELETRÔNICA", "Simulador de votação • ambiente educacional"), BorderLayout.NORTH);
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(new Color(242, 245, 249));
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210, 218, 228)),
                BorderFactory.createEmptyBorder(35, 55, 35, 55)));
        JLabel texto = new JLabel("<html><center><font size='5'>Pronto para começar?</font><br><br>Serão apresentados dois cargos fictícios.<br>Todos os votos são anônimos.</center></html>");
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton iniciarButton = botao("INICIAR NOVA SIMULAÇÃO");
        iniciarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        iniciarButton.addActionListener(e -> { dispose(); iniciar.run(); });
        card.add(texto); card.add(Box.createVerticalStrut(25)); card.add(iniciarButton);
        JButton cadastrarButton = botao("CADASTRAR CANDIDATO");
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadastrarButton.addActionListener(e -> cadastrar.run());
        card.add(Box.createVerticalStrut(10)); card.add(cadastrarButton);
        centro.add(card);
        add(centro, BorderLayout.CENTER);
        pack();
        setSize(900, 600);
    }
}
