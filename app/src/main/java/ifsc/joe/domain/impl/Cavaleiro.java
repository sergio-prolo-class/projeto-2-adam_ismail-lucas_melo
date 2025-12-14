package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.interfaces.ComMontaria;
import ifsc.joe.domain.interfaces.Lutador;
import ifsc.joe.domain.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Cavaleiro extends Personagem implements Lutador, ComMontaria {

    private boolean montado = true;
    private Image icone;

    // velocidades configuráveis
    private final int velMontado = Config.getInt("cavaleiro.velocidade");
    private final int velDesmontado = Math.max(1, velMontado - 2);

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
        this.alcanceAtaque = Config.getInt("cavaleiro.alcance"); 

        // estado inicial montado
        this.montado = true;
        this.icone = carregarImagem("cavaleiro");
    }

    // -------------------------------
    //   MONTARIA
    // -------------------------------

    @Override
    public void montar() {
        if (!montado) {
            montado = true;
            velocidade = velMontado;
            icone = carregarImagem("cavaleiro"); // sprite montado
            System.out.println("Cavaleiro " + id + " montou.");
        }
    }

    @Override
    public void desmontar() {
        if (montado) {
            montado = false;
            velocidade = velDesmontado;
            icone = carregarImagem("guerreiro"); // sprite desmontado
            System.out.println("Cavaleiro " + id + " desmontou.");
        }
    }

    @Override
    public boolean estaMontado() {
        return montado;
    }

    // -------------------------------
    //   ATAQUE
    // -------------------------------

    @Override
    public void atacar(Personagem alvo) {
        if (!vivo || alvo == null) return;

        this.atacando = true;
        alvo.receberDano(this.ataque);
    }

    // -------------------------------
    //   ATUALIZAÇÃO
    // -------------------------------

    @Override
    public void atualizar() {
        // sem lógica contínua específica
    }

    // -------------------------------
    //   DESENHO (com fade-out)
    // -------------------------------
    @Override
    public void desenhar(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        // desenha alcance ao atacar
        if (atacando) {
            int raio = alcanceAtaque;

            int cx = x + 20;
            int cy = y + 20;

            g2.setColor(new Color(255, 80, 80, 80));
            g2.fillOval(cx - raio, cy - raio, raio * 2, raio * 2);
        }

        // sprite
        g2.setComposite(
            AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
        );
        g2.drawImage(icone, x, y, null);

        g2.dispose();

        // reseta estado de ataque visual
        atacando = false;
    }


    // -------------------------------
    //   CARREGAR IMAGENS
    // -------------------------------

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png")
        )).getImage();
    }
}