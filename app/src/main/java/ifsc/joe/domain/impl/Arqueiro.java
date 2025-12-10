package ifsc.joe.domain.impl;

import java.awt.Color;
import java.awt.Graphics;

import ifsc.joe.domain.Config;
import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.interfaces.Lutador;
import ifsc.joe.domain.interfaces.Coletador;

public class Arqueiro extends Personagem implements Lutador, Coletador 
{
    private final int alcance;
    private final long cooldownAtaque;
    private final long cooldownColeta;
    private long ultimoAtaque = 0;
    private long ultimaColeta = 0;

    public Arqueiro(int x, int y) 
    {
        super(
            x,
            y,
            Config.getInt("arqueiro.vida"),
            Config.getInt("arqueiro.ataque"),
            Config.getInt("arqueiro.velocidade"),
            Config.getInt("arqueiro.esquiva")
        );

        this.alcance = Config.getInt("arqueiro.alcance");
        this.cooldownAtaque = Config.getInt("arqueiro.ataque_cooldown");
        this.cooldownColeta = Config.getInt("arqueiro.coleta_cooldown");
    }

    @Override
    public void atualizar() 
    {
    }

    @Override
    public void desenhar(Graphics g)
    {
        g.setColor(Color.GREEN.darker());
        g.fillRect(x, y, 20, 20);

        g.setColor(Color.RED);
        int barra = Math.max(0, Math.min(16, (int)((16.0 * vida) / Config.getInt("arqueiro.vida"))));
        g.fillRect(x, y - 6, barra, 4);

        if (atacando) 
        {
            g.setColor(new Color(0, 255, 0, 60));
            int centerX = x + 10;
            int centerY = y + 10;
            int diam = alcance * 2;

            g.fillOval(centerX - alcance, centerY - alcance, diam, diam);

            g.setColor(Color.GREEN);
            g.drawOval(centerX - alcance, centerY - alcance, diam, diam);
        }
    }

    @Override
    public void atacar(Personagem alvo) 
    {
        if (!vivo || alvo == null || !alvo.isVivo()) return;

        long agora = System.currentTimeMillis();
        if (agora - ultimoAtaque < cooldownAtaque) return;

        double dx = alvo.getX() - this.x;
        double dy = alvo.getY() - this.y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia <= alcance) 
        {
            this.atacando = true;
            alvo.receberDano(this.ataque);
            ultimoAtaque = agora;

            new Thread(() -> {
                try { Thread.sleep(150); } 
                catch (InterruptedException ignored) {}
                atacando = false;
            }).start();
        }
    }

    @Override
    public void coletar(Personagem p) 
    {
        if (!vivo) return;

        long agora = System.currentTimeMillis();
        if (agora - ultimaColeta < cooldownColeta) return;

        ultimaColeta = agora;
    }
}
