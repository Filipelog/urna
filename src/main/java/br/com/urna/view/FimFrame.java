package br.com.urna.view;

import javax.swing.*;
import java.awt.*;

public final class FimFrame extends TelaBase {
    public FimFrame(Runnable novaSimulacao) {
        super("Urna Eletrônica - Fim");
        setLayout(new BorderLayout());
        add(painelTitulo("FIM", "Simulação encerrada"), BorderLayout.NORTH);
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(new Color(242, 245, 249));
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(35, 60, 35, 60));
        JLabel msg = new JLabel("<html><center><font size='6'>Votação concluída!</font><br><br>Obrigado por participar da simulação.</center></html>");
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton novo = botao("INICIAR NOVA SIMULAÇÃO"); novo.setAlignmentX(Component.CENTER_ALIGNMENT);
        novo.addActionListener(e -> { dispose(); novaSimulacao.run(); });
        card.add(msg); card.add(Box.createVerticalStrut(25)); card.add(novo); centro.add(card); add(centro);
        pack(); setSize(900, 600);
    }
}
