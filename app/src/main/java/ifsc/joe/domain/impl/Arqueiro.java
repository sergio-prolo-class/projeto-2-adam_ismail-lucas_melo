package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.interfaces.Lutador;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Arqueiro extends Personagem implements Lutador {

    private Image icone;

    public Arqueiro(int x, int y, int id) {
        super(
                x, y,
                60,   // vida
                12,   // ataque
                6,    // velocidade
                15    // esquiva
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