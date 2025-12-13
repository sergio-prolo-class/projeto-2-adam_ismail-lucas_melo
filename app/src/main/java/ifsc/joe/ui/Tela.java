package ifsc.joe.ui;

import ifsc.joe.domain.GameEngine;
import ifsc.joe.domain.Personagem;
import ifsc.joe.enums.Direcao;
import ifsc.joe.domain.Config;
import ifsc.joe.ui.FiltroTipo;
import ifsc.joe.domain.interfaces.ComMontaria;

import javax.swing.*;
import java.awt.*;

public class Tela extends JPanel {

    private final GameEngine engine;

    public Tela() {
        this.setBackground(Color.white);
        this.engine = new GameEngine();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Personagem p : engine.getPersonagens()) {
            if (p.isVivo()) {
                p.desenhar(g);
            }
        }
    }

    // ---------------------------
    //  CRIAÇÃO DE PERSONAGENS
    // ---------------------------

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

    // ---------------------------
    //  MOVIMENTAÇÃO
    // ---------------------------

    public void movimentarAldeoes(Direcao d) {
        for (Personagem p : engine.getPersonagens()) {
            p.mover(
                d == Direcao.DIREITA ? 1 :
                d == Direcao.ESQUERDA ? -1 : 0,
                d == Direcao.BAIXO ? 1 :
                d == Direcao.CIMA ? -1 : 0,
                this.getWidth(),
                this.getHeight()
            );
        }
        repaint();
    }

    public void movimentarFiltrados(Direcao d, FiltroTipo filtro) {
        for (Personagem p : engine.getPersonagens()) {

            if (!aplicaFiltro(p, filtro)) continue;

            p.mover(
                d == Direcao.DIREITA ? 1 :
                d == Direcao.ESQUERDA ? -1 : 0,
                d == Direcao.BAIXO ? 1 :
                d == Direcao.CIMA ? -1 : 0,
                this.getWidth(),
                this.getHeight()
            );
        }
        repaint();
    }

    // ---------------------------
    //  ATAQUE
    // ---------------------------

    public void atacar() {
        engine.atacarGuerreiros(Config.getInt("engine.colisao.distancia"));
        repaint();
    }

    public void atacarFiltrados(FiltroTipo filtro) {

        // efeito visual
        for (Personagem p : engine.getPersonagens()) {
            if (aplicaFiltro(p, filtro)) {
                p.setAtacando(true);
            }
        }

        // ataque real
        engine.atacarFiltrados(filtro);

        repaint();
    }

    // ---------------------------
    //  CONTROLE DE MONTARIA
    // ---------------------------

    public void alternarMontariaFiltrados(FiltroTipo filtro) {

        for (Personagem p : engine.getPersonagens()) {

            if (!aplicaFiltro(p, filtro)) continue;
            if (!(p instanceof ComMontaria cm)) continue;

            if (cm.estaMontado()) {
                cm.desmontar();
            } else {
                cm.montar();
            }
        }

        repaint();
    }

    // ---------------------------
    //  AUXILIAR
    // ---------------------------

    private boolean aplicaFiltro(Personagem p, FiltroTipo filtro) {
        switch (filtro) {
            case TODOS:
                return true;
            case ALDEAO:
                return p instanceof ifsc.joe.domain.impl.Aldeao;
            case ARQUEIRO:
                return p instanceof ifsc.joe.domain.impl.Arqueiro;
            case CAVALEIRO:
                return p instanceof ifsc.joe.domain.impl.Cavaleiro;
            default:
                return false;
        }
    }

    public GameEngine getEngine() {
        return engine;
    }
}