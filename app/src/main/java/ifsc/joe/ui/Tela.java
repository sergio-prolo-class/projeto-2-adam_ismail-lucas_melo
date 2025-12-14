package ifsc.joe.ui;

import ifsc.joe.domain.GameEngine;
import ifsc.joe.domain.Personagem;
import ifsc.joe.enums.Direcao;
import ifsc.joe.domain.interfaces.ComMontaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Random;

public class Tela extends JPanel {

    // estado principal
    private final GameEngine engine;
    private final Random sorteio = new Random();
    private final PainelControles controles;

    public Tela(PainelControles controles) {
        this.controles = controles;
        this.engine = new GameEngine();
        this.setBackground(Color.white);

        setFocusable(true);
        requestFocusInWindow();

        // desativa navegação padrão do TAB
        setFocusTraversalKeysEnabled(false);
        setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                Collections.emptySet()
        );
        setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
                Collections.emptySet()
        );

        configurarTeclas();
    }

    // ----------------------------------------
    //  DESENHO
    // ----------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Personagem p : engine.getPersonagens()) {

            if (!p.isVivo()) continue;

            desenharBarraVida(g, p);
            p.desenhar(g);
        }
    }

    // barra de vida acima do personagem
    private void desenharBarraVida(Graphics g, Personagem p) {

        int larguraTotal = 30;
        int altura = 5;

        int x = p.getX();
        int y = p.getY() - 8;

        double perc = (double) p.getVida() / p.getVidaMaxima();
        int larguraAtual = (int) (larguraTotal * perc);

        Color cor;
        if (perc > 0.75) {
            cor = new Color(60, 180, 75);      // verde
        } else if (perc > 0.25) {
            cor = new Color(240, 200, 20);    // amarelo
        } else {
            cor = new Color(220, 60, 60);     // vermelho
        }

        Graphics2D g2 = (Graphics2D) g.create();

        // fundo
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRect(x, y, larguraTotal, altura);

        // vida atual
        g2.setColor(cor);
        g2.fillRect(x, y, larguraAtual, altura);

        g2.dispose();
    }

    // ----------------------------------------
    //  CRIAÇÃO
    // ----------------------------------------
    public void criarAldeao(int x, int y) {
        engine.adicionarPersonagem(
                new ifsc.joe.domain.impl.Aldeao(x, y, engine.getNextId())
        );
        repaint();
    }

    public void criarArqueiro(int x, int y) {
        engine.adicionarPersonagem(
                new ifsc.joe.domain.impl.Arqueiro(x, y, engine.getNextId())
        );
        repaint();
    }

    public void criarCavaleiro(int x, int y) {
        engine.adicionarPersonagem(
                new ifsc.joe.domain.impl.Cavaleiro(x, y, engine.getNextId())
        );
        repaint();
    }

    // ----------------------------------------
    //  MOVIMENTO
    // ----------------------------------------
    public void movimentarFiltrados(Direcao d, FiltroTipo filtro) {
        for (Personagem p : engine.getPersonagens()) {

            if (!aplicaFiltro(p, filtro)) continue;

            p.mover(
                    d == Direcao.DIREITA ? 1 :
                    d == Direcao.ESQUERDA ? -1 : 0,
                    d == Direcao.BAIXO ? 1 :
                    d == Direcao.CIMA ? -1 : 0,
                    getWidth(),
                    getHeight()
            );
        }
        repaint();
    }

    private void mover(Direcao d) {
        movimentarFiltrados(d, getFiltro());
    }

    // ----------------------------------------
    //  ATAQUE
    // ----------------------------------------
    public void atacarFiltrados(FiltroTipo filtro) {

        for (Personagem p : engine.getPersonagens()) {
            if (aplicaFiltro(p, filtro)) {
                p.setAtacando(true);
            }
        }

        engine.atacarFiltrados(filtro);
        repaint();
    }

    // ----------------------------------------
    //  MONTARIA
    // ----------------------------------------
    public void alternarMontariaFiltrados(FiltroTipo filtro) {

        for (Personagem p : engine.getPersonagens()) {

            if (!aplicaFiltro(p, filtro)) continue;
            if (!(p instanceof ComMontaria cm)) continue;

            if (cm.estaMontado()) cm.desmontar();
            else cm.montar();
        }

        repaint();
    }

    // ----------------------------------------
    //  FILTRO
    // ----------------------------------------
    private boolean aplicaFiltro(Personagem p, FiltroTipo filtro) {
        return switch (filtro) {
            case TODOS -> true;
            case ALDEAO -> p instanceof ifsc.joe.domain.impl.Aldeao;
            case ARQUEIRO -> p instanceof ifsc.joe.domain.impl.Arqueiro;
            case CAVALEIRO -> p instanceof ifsc.joe.domain.impl.Cavaleiro;
        };
    }

    private FiltroTipo getFiltro() {
        return controles.getFiltroAtual();
    }

    private void alternarFiltro() {
        controles.cycleFiltro();
    }

    // ----------------------------------------
    //  TECLADO
    // ----------------------------------------
    private void configurarTeclas() {

        bind("up", KeyStroke.getKeyStroke("W"), () -> mover(Direcao.CIMA));
        bind("up2", KeyStroke.getKeyStroke("UP"), () -> mover(Direcao.CIMA));

        bind("down", KeyStroke.getKeyStroke("S"), () -> mover(Direcao.BAIXO));
        bind("down2", KeyStroke.getKeyStroke("DOWN"), () -> mover(Direcao.BAIXO));

        bind("left", KeyStroke.getKeyStroke("A"), () -> mover(Direcao.ESQUERDA));
        bind("left2", KeyStroke.getKeyStroke("LEFT"), () -> mover(Direcao.ESQUERDA));

        bind("right", KeyStroke.getKeyStroke("D"), () -> mover(Direcao.DIREITA));
        bind("right2", KeyStroke.getKeyStroke("RIGHT"), () -> mover(Direcao.DIREITA));

        bind("criar1", KeyStroke.getKeyStroke("1"), () -> {
            Point p = gerarPosicaoAleatoria();
            criarAldeao(p.x, p.y);
        });

        bind("criar2", KeyStroke.getKeyStroke("2"), () -> {
            Point p = gerarPosicaoAleatoria();
            criarArqueiro(p.x, p.y);
        });

        bind("criar3", KeyStroke.getKeyStroke("3"), () -> {
            Point p = gerarPosicaoAleatoria();
            criarCavaleiro(p.x, p.y);
        });

        bind("atacar", KeyStroke.getKeyStroke("SPACE"),
                () -> atacarFiltrados(getFiltro()));

        bind("filtro", KeyStroke.getKeyStroke("TAB"), this::alternarFiltro);

        bind("montaria", KeyStroke.getKeyStroke("M"),
                () -> alternarMontariaFiltrados(getFiltro()));
    }

    private void bind(String name, KeyStroke key, Runnable action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(key, name);
        getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    // ----------------------------------------
    //  UTIL
    // ----------------------------------------
    private Point gerarPosicaoAleatoria() {
        final int PADDING = 50;
        int x = sorteio.nextInt(Math.max(1, getWidth() - PADDING));
        int y = sorteio.nextInt(Math.max(1, getHeight() - PADDING));
        return new Point(x, y);
    }

    public GameEngine getEngine() {
        return engine;
    }
}