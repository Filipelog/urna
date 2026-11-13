package br.com.urna.view;

import javax.swing.*;
import java.awt.*;

public abstract class TelaBase extends JFrame {
    protected TelaBase(String titulo) {
        super(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
    }
    protected JButton botao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 15));
        botao.setFocusPainted(false);
        return botao;
    }
    protected JPanel painelTitulo(String titulo, String subtitulo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(28, 54, 91));
        painel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        JLabel label = new JLabel("<html><font color='white' size='6'>" + titulo + "</font><br><font color='#d7e8ff'>" + subtitulo + "</font></html>");
        painel.add(label);
        return painel;
    }
}
