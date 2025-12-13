package ifsc.joe.ui;

import ifsc.joe.domain.GameEngine;
import ifsc.joe.domain.Personagem;
import ifsc.joe.enums.Direcao;
import ifsc.joe.domain.Config;
import ifsc.joe.domain.interfaces.ComMontaria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Random;

public class Tela extends JPanel {

    // ----------------------------------------
    //  ESTADO PRINCIPAL
    // ----------------------------------------
    private final GameEngine engine;
    private final Random sorteio = new Random();
    private final PainelControles controles;

    // ----------------------------------------
    //  CONSTRUTOR
    // ----------------------------------------
    public Tela(PainelControles controles) {
        this.controles = controles;
        this.engine = new GameEngine();
        this.setBackground(Color.white);

        setFocusable(true);
        requestFocusInWindow();

        // desativa comportamento padrão do TAB
        setFocusTraversalKeysEnabled(false);
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());

        configurarTeclas();
    }

    // ----------------------------------------
    //  DESENHO
    // ----------------------------------------
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Personagem p : engine.getPersonagens()) {
            if (p.isVivo()) {
                p.desenhar(g);
            }
        }
    }

    // ----------------------------------------
    //  CRIAÇÃO DE PERSONAGENS
    // ----------------------------------------
    public void criarAldeao(int x, int y) {
        engine.adicionarPersonagem(new ifsc.joe.domain.impl.Aldeao(x, y, engine.getNextId()));
        repaint();
    }

    public void criarArqueiro(int x, int y) {
        engine.adicionarPersonagem(new ifsc.joe.domain.impl.Arqueiro(x, y, engine.getNextId()));
        repaint();
    }

    public void criarCavaleiro(int x, int y) {
        engine.adicionarPersonagem(new ifsc.joe.domain.impl.Cavaleiro(x, y, engine.getNextId()));
        repaint();
    }

    // ----------------------------------------
    //  MOVIMENTAÇÃO
    // ----------------------------------------
    public void movimentarFiltrados(Direcao d, FiltroTipo filtro) {
        for (Personagem p : engine.getPersonagens()) {
            if (!aplicaFiltro(p, filtro)) continue;

            p.mover(
                d == Direcao.DIREITA ? 1 : d == Direcao.ESQUERDA ? -1 : 0,
                d == Direcao.BAIXO ? 1 : d == Direcao.CIMA ? -1 : 0,
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
        // efeito visual
        for (Personagem p : engine.getPersonagens()) {
            if (aplicaFiltro(p, filtro)) {
                p.setAtacando(true);
            }
        }

        // dano real
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
    //  KEYBINDINGS
    // ----------------------------------------
    private void configurarTeclas() {

        // movimento
        bind("move_up", KeyStroke.getKeyStroke("W"), () -> mover(Direcao.CIMA));
        bind("move_up2", KeyStroke.getKeyStroke("UP"), () -> mover(Direcao.CIMA));

        bind("move_down", KeyStroke.getKeyStroke("S"), () -> mover(Direcao.BAIXO));
        bind("move_down2", KeyStroke.getKeyStroke("DOWN"), () -> mover(Direcao.BAIXO));

        bind("move_left", KeyStroke.getKeyStroke("A"), () -> mover(Direcao.ESQUERDA));
        bind("move_left2", KeyStroke.getKeyStroke("LEFT"), () -> mover(Direcao.ESQUERDA));

        bind("move_right", KeyStroke.getKeyStroke("D"), () -> mover(Direcao.DIREITA));
        bind("move_right2", KeyStroke.getKeyStroke("RIGHT"), () -> mover(Direcao.DIREITA));

        // criação rápida
        bind("criar_aldeao", KeyStroke.getKeyStroke("1"), () -> {
            Point p = gerarPosicaoAleatoria();
            criarAldeao(p.x, p.y);
        });

        bind("criar_arqueiro", KeyStroke.getKeyStroke("2"), () -> {
            Point p = gerarPosicaoAleatoria();
            criarArqueiro(p.x, p.y);
        });

        bind("criar_cavaleiro", KeyStroke.getKeyStroke("3"), () -> {
            Point p = gerarPosicaoAleatoria();
            criarCavaleiro(p.x, p.y);
        });

        // ataque
        bind("atacar", KeyStroke.getKeyStroke("SPACE"), () -> atacarFiltrados(getFiltro()));

        // alternar filtro
        bind("filtro", KeyStroke.getKeyStroke("TAB"), this::alternarFiltro);

        // montaria
        bind("montaria", KeyStroke.getKeyStroke("M"), () -> alternarMontariaFiltrados(getFiltro()));
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
    //  UTILITÁRIOS
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