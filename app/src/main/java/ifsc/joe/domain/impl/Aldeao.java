package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.interfaces.Coletador;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import ifsc.joe.domain.Config;

public class Aldeao extends Personagem implements Coletador {

    private Image icone;

    public Aldeao(int x, int y, int id) {
        super(
                x, 
                y,
                Config.getInt("aldeao.vida"),
                Config.getInt("aldeao.ataque"),
                Config.getInt("aldeao.velocidade"),
                Config.getInt("aldeao.esquiva")
        );
        this.id = id;
        this.atacando = false;
        this.icone = carregarImagem("aldeao");
    }

    @Override
    public void atualizar() {
        // aldeão não tem lógica especial de atualização ainda
        // aqui podemos colocar coleta/mineração se quiser depois
    }

    @Override
    public void desenhar(Graphics g) {
        String nomeImg = atacando ? "aldeao2" : "aldeao";
        this.icone = carregarImagem(nomeImg);
        g.drawImage(this.icone, x, y, null);
    }

    @Override
    public void coletar(Personagem p) {
        System.out.println("Aldeão " + id + " está coletando recursos de " + p.getId());
    }


    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png")
        )).getImage();
    }
}