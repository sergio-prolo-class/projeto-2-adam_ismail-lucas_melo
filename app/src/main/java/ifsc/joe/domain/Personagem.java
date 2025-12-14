package ifsc.joe.domain;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Personagem {

    protected int id;
    protected int x;
    protected int y;

    protected int vida;
    protected int vidaMaxima;

    protected int ataque;
    protected int velocidade;
    protected int esquiva;

    protected boolean vivo;
    protected boolean atacando;

    protected float alpha = 1.0f;

    public Personagem(int x, int y, int vida, int ataque, int velocidade, int esquiva) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataque = ataque;
        this.velocidade = velocidade;
        this.esquiva = esquiva;
        this.vivo = true;
        this.atacando = false;
    }

    // ----------------------------------------
    //  CICLO DE ATUALIZAÇÃO
    // ----------------------------------------
    public void atualizar() {
        // personagem morto entra em fade-out
        if (!vivo && alpha > 0) {
            reduzirAlpha(0.04f); // controla a velocidade do fade
        }
    }

    public abstract void desenhar(Graphics g);

    // ----------------------------------------
    //  MOVIMENTO
    // ----------------------------------------
    public void mover(int dx, int dy, int larg, int alt) {
        if (!vivo) return;

        x += dx * velocidade;
        y += dy * velocidade;

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > larg - 20) x = larg - 20;
        if (y > alt - 20) y = alt - 20;
    }

    // ----------------------------------------
    //  COMBATE
    // ----------------------------------------
    public void receberDano(int valor) {
        if (!vivo) return;

        int sorteio = (int) (Math.random() * 100) + 1;

        if (sorteio <= esquiva) {
            esquivar();
            return;
        }

        vida -= valor;

        if (vida <= 0) {
            vida = 0;
            vivo = false;
            alpha = 1.0f; // começa o fade totalmente visível
        }
    }

    protected void esquivar() {
        System.out.println("O personagem " + id + " desviou do ataque!");
    }

    // ----------------------------------------
    //  ESTADO
    // ----------------------------------------
    public boolean isVivo() {
        return vivo;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean a) {
        this.atacando = a;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    // ----------------------------------------
    //  FADE
    // ----------------------------------------
    public float getAlpha() {
        return alpha;
    }

    public void reduzirAlpha(float passo) {
        alpha -= passo;
        if (alpha < 0) alpha = 0;
    }

    // ----------------------------------------
    //  GETTERS
    // ----------------------------------------
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }

    public int getAtaque() { return ataque; }
    public int getVelocidade() { return velocidade; }
}