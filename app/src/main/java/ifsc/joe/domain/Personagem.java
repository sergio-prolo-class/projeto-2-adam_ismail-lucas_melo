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
    protected boolean vivo;
    protected boolean atacando;

    public Personagem(int x, int y, int vida, int ataque, int velocidade) 
    {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.ataque = ataque;
        this.velocidade = velocidade;
        this.vivo = true;
        this.atacando = false;
    }

    
}