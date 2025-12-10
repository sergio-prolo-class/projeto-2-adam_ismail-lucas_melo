package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.Interfaces.Lutador;
import ifsc.joe.domain.Interfaces.ComMontaria;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Cavaleiro extends Personagem implements Lutador, ComMontaria {

    private Image icone;
    private boolean montado;

    public Cavaleiro(int x, int y, int id) {
        super(
                x, y,
                100, // vida
                20,  // ataque
                8,   // velocidade
                5    // esquiva
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
        g.drawImage(carregarImagem(nome), x, y, null);

    }

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png")
        )).getImage();
    }
}
