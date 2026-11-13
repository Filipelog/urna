package br.com.urna.view;

import br.com.urna.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;

public final class VotacaoFrame extends TelaBase {
    private final Cargo cargo;
    private final List<Candidato> candidatos;
    private final JTextField numero = new JTextField();
    private final JLabel identificacao = new JLabel("Digite o número do candidato", SwingConstants.CENTER);
    private final JLabel foto = new JLabel();
    private final Consumer<Voto> confirmar;
    private String digitado = "";
    private boolean brancoSelecionado;

    public VotacaoFrame(Cargo cargo, List<Candidato> candidatos, Consumer<Voto> confirmar) {
        super("Urna Eletrônica - " + cargo.getNome());
        this.cargo = cargo; this.candidatos = candidatos; this.confirmar = confirmar;
        construir();
    }

    private void construir() {
        setLayout(new BorderLayout(12, 12));
        add(painelTitulo(cargo.getNome().toUpperCase(), cargo.getTipo() + " • " + cargo.getQuantidadeDigitos() + " dígitos"), BorderLayout.NORTH);
        JPanel corpo = new JPanel(new GridLayout(1, 2, 20, 0));
        corpo.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));
        corpo.add(criarDisplay());
        corpo.add(criarTeclado());
        add(corpo, BorderLayout.CENTER);
        pack(); setSize(900, 600);
    }

    private JPanel criarDisplay() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Seu voto"));
        numero.setEditable(false); numero.setFont(new Font("Monospaced", Font.BOLD, 34)); numero.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(numero, BorderLayout.NORTH);
        identificacao.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JPanel candidatoPanel = new JPanel(new BorderLayout(12, 0));
        foto.setPreferredSize(new Dimension(140, 140));
        candidatoPanel.add(foto, BorderLayout.WEST);
        candidatoPanel.add(identificacao, BorderLayout.CENTER);
        painel.add(candidatoPanel, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarTeclado() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        JPanel teclas = new JPanel(new GridLayout(4, 3, 8, 8));
        for (int i = 1; i <= 9; i++) adicionarTecla(teclas, String.valueOf(i));
        adicionarTecla(teclas, "BRANCO"); adicionarTecla(teclas, "0"); adicionarTecla(teclas, "CORRIGE");
        painel.add(teclas, BorderLayout.CENTER);
        JButton confirma = botao("CONFIRMA");
        confirma.setBackground(new Color(53, 142, 71)); confirma.setForeground(Color.WHITE);
        confirma.addActionListener(e -> confirmarVoto());
        painel.add(confirma, BorderLayout.SOUTH);
        return painel;
    }

    private void adicionarTecla(JPanel painel, String texto) {
        JButton tecla = botao(texto);
        tecla.addActionListener(e -> {
            if ("CORRIGE".equals(texto)) limpar();
            else if ("BRANCO".equals(texto)) { brancoSelecionado = true; digitado = ""; numero.setText(""); identificacao.setText("VOTO EM BRANCO"); }
            else if (digitado.length() < cargo.getQuantidadeDigitos()) { brancoSelecionado = false; digitado += texto; numero.setText(digitado); atualizarCandidato(); }
        });
        painel.add(tecla);
    }

    private void atualizarCandidato() {
        Candidato candidato = candidatos.stream().filter(c -> c.getNumero().equals(digitado)).findFirst().orElse(null);
        if (candidato == null) {
            foto.setIcon(null);
            identificacao.setText("Número não identificado");
        } else {
            foto.setIcon(new ImageIcon(criarAvatar(candidato)));
            identificacao.setText("<html><center>" + candidato.getNome() + "<br>" + candidato.getPartido() + "</center></html>");
        }
    }
    private void limpar() { brancoSelecionado = false; digitado = ""; numero.setText(""); foto.setIcon(null); identificacao.setText("Digite o número do candidato"); }
    private void confirmarVoto() {
        if (brancoSelecionado) { confirmar.accept(Voto.branco(cargo.getId())); return; }
        if (digitado.isEmpty()) { JOptionPane.showMessageDialog(this, "Digite um número ou use BRANCO."); return; }
        if (candidatos.stream().noneMatch(c -> c.getNumero().equals(digitado))) { JOptionPane.showMessageDialog(this, "Número não identificado."); return; }
        confirmar.accept(Voto.nominal(cargo.getId(), digitado));
    }

    private BufferedImage criarAvatar(Candidato candidato) {
        BufferedImage imagem = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagem.createGraphics();
        g.setColor(new Color(42, 91, 145)); g.fillRect(0, 0, 140, 140);
        g.setColor(Color.WHITE); g.setFont(new Font("SansSerif", Font.BOLD, 42));
        String[] nomes = candidato.getNome().split(" ");
        String iniciais = nomes[0].substring(0, 1) + (nomes.length > 1 ? nomes[nomes.length - 1].substring(0, 1) : "");
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(iniciais, (140 - metrics.stringWidth(iniciais)) / 2, 85);
        g.dispose();
        return imagem;
    }
}
