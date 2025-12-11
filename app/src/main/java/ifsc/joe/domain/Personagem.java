package ifsc.joe.domain;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Personagem 
{
    protected int id;
    protected int x;
    protected int y;
    protected int vida;
    protected int ataque;
    protected int velocidade;
    protected int esquiva;
    protected boolean vivo;
    protected boolean atacando;

    protected float alpha = 1.0f;

    public float getAlpha() {
        return alpha;
    }

    public void reduzirAlpha(float passo) {
        alpha -= passo;
        if (alpha < 0) alpha = 0;
    }

    public Personagem(int x, int y, int vida, int ataque, int velocidade, int esquiva) 
    {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.ataque = ataque;
        this.velocidade = velocidade;
        this.esquiva = esquiva;
        this.vivo = true;
        this.atacando = false;
    }

    public abstract void atualizar();
    public abstract void desenhar(Graphics g);

    public void mover(int dx, int dy, int larg, int alt) 
    {
        if (!vivo) return;
        x += dx * velocidade;
        y += dy * velocidade;

        // limites de tela
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > larg - 20) x = larg - 20;
        if (y > alt - 20) y = alt - 20;
    }

    
    public void receberDano(int valor) 
    {
        if (!vivo) return;

        int sorteio = (int) (Math.random() * 100) + 1;
                
        if (sorteio <= esquiva)
        {
            esquivar();
            return;
        }

        vida -= valor;

        if (vida <= 0)
        {
            vida = 0;
            vivo = false;
            alpha = 1.0f;
        } 
    }

    protected void esquivar() 
    {
        System.out.println("O personagem " + id + " desviou do ataque!");
    }

    public boolean isVivo()
    {
        return vivo;
    }

    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, 20, 20);
    }

    public void setAtacando(boolean a)
    {
        this.atacando = a;
    }

    
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getX() {return x;}
    public int getY() {return y;}
    public int getVida() {return vida;}
    public int getAtaque() {return ataque;}
    public int getVelocidade() {return velocidade;}

}