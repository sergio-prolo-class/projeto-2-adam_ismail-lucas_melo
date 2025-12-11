package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.interfaces.ComMontaria;
import ifsc.joe.domain.interfaces.Lutador;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import ifsc.joe.domain.Config;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;


public class Cavaleiro extends Personagem implements Lutador, ComMontaria {

    private Image icone;
    private boolean montado;

    public Cavaleiro(int x, int y, int id) {
        super(
                x,
                y,
                Config.getInt("cavaleiro.vida"),
                Config.getInt("cavaleiro.ataque"),
                Config.getInt("cavaleiro.velocidade"),
                Config.getInt("cavaleiro.esquiva")
        );
        this.id = id;
        this.atacando = false;
        this.montado = true;
        this.icone = carregarImagem("cavaleiro");
    }

    @Override
    public void montar() {
        this.montado = true;
        System.out.println("Cavaleiro " + id + " montou no cavalo.");
    }

    @Override
    public void desmontar() {
        this.montado = false;
        System.out.println("Cavaleiro " + id + " desmontou do cavalo.");
    }

    @Override
    public boolean estaMontado() {
        return this.montado;
    }

    @Override
    public void atacar(Personagem alvo) {
        if (!vivo || alvo == null) return;

        this.atacando = true;
        alvo.receberDano(this.ataque);
    }

    @Override
    public void atualizar() {

    }

    @Override
    public void desenhar(Graphics g) {
        String nome = atacando ? "cavaleiro" : "cavaleiro";
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(icone, x, y, null);
        g2.dispose();
    }

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png")
        )).getImage();
    }
}
