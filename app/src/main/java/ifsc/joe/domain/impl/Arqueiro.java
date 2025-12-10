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
        String nome = atacando ? "arqueiro" : "arqueiro";
        g.drawImage(carregarImagem(nome), x, y, null);
    
    }

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png")
        )).getImage();
    }
}