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
        this.alcanceAtaque = Config.getInt("aldeao.alcance"); 
    }

    @Override
    public void atualizar() {
        // aldeão não tem lógica especial de atualização ainda
        // aqui podemos colocar coleta/mineração se quiser depois
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
        String nomeImg = atacando ? "aldeao2" : "aldeao";
        this.icone = carregarImagem(nomeImg);

        g2.setComposite(
            AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
        );
        g2.drawImage(icone, x, y, null);

        g2.dispose();

        // limpa estado visual
        atacando = false;
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