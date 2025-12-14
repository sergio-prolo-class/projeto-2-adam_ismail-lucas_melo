package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.interfaces.Lutador;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import ifsc.joe.domain.Config;

public class Arqueiro extends Personagem implements Lutador {

    private Image icone;

    public Arqueiro(int x, int y, int id) {
        super(
                x,
                y,
                Config.getInt("arqueiro.vida"),
                Config.getInt("arqueiro.ataque"),
                Config.getInt("arqueiro.velocidade"),
                Config.getInt("arqueiro.esquiva")
        );
        this.id = id;
        this.atacando = false;
        this.icone = carregarImagem("arqueiro");
        this.alcanceAtaque = Config.getInt("arqueiro.alcance"); 
    }

    @Override
    public void atacar(Personagem alvo) {
        if (!vivo) return;
        if (alvo == null) return;

        this.atacando = true;
        alvo.receberDano(this.ataque);
    }

    @Override
    public void atualizar() {
        // arqueiro não tem lógica extra ainda
    }

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

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png")
        )).getImage();
    }
}